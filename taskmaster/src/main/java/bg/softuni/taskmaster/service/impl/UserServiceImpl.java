package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.PictureService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    private static final String USERS_PROFILE_PICTURES_FOLDER = "users/profile-pictures";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final PictureService pictureService;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) throws IOException {
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.getRoles().add(roleRepository.getByName(UserRoles.USER));
        setProfilePicture(userRegisterDTO.getProfilePicture(), user);
        userRepository.save(user);
    }


    @Override
    public UserInfoDTO getInfo(Long id) {
        return modelMapper.map(userHelperService.getUser(id), UserInfoDTO.class);
    }

    private UserInfoDTO toInfo(User e) {
        UserInfoDTO userInfoDTO = modelMapper.map(e, UserInfoDTO.class);
        userInfoDTO.setAdmin(userHelperService.isAdmin(e.getId()));
        return userInfoDTO;
    }

    @Override
    public void edit(UserEditDTO userEditDTO) throws IOException {
        User user = userHelperService.getLoggedUser();
        BeanUtils.copyProperties(userEditDTO, user);
        changeProfilePicture(userEditDTO, user);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDTO) {
        User user = userHelperService.getLoggedUser();
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }


    @Override
    public Page<UserInfoDTO> getAll(String searchQuery, Pageable pageable) {
        if (searchQuery.isEmpty()) {
            return userRepository.findAll(pageable).map(this::toInfo);
        }
        return userRepository.findAllBySearchQuery(searchQuery, pageable).map(this::toInfo);
    }

    @Override
    public UserEditDTO getCurrentUserEditData() {
        return modelMapper.map(userHelperService.getLoggedUser(), UserEditDTO.class);
    }

    private void changeProfilePicture(UserEditDTO userEditDTO, User user) throws IOException {
        if (user.getProfilePicture().getId() == 1 && userEditDTO.getProfilePicture().isEmpty()) {
            return;
        }
        Picture oldprofilePicture = user.getProfilePicture();
        setProfilePicture(userEditDTO.getProfilePicture(), user);
        if (oldprofilePicture.getId() != 1) pictureService.deletePicture(oldprofilePicture);
    }

    private void setProfilePicture(MultipartFile profilePicture, User user) throws IOException {
        if (profilePicture.isEmpty()) {
            user.setProfilePicture(pictureService.getDefultProfilePicture());
        } else {
            user.setProfilePicture(pictureService.createPicture(profilePicture, USERS_PROFILE_PICTURES_FOLDER, String.valueOf(UUID.randomUUID())));
        }
        userRepository.saveAndFlush(user);
    }
}
