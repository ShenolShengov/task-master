package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.PictureService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserModificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static bg.softuni.taskmaster.utils.PictureUtils.USERS_PROFILE_PICTURES_FOLDER;

@Service
@RequiredArgsConstructor
public class UserModificationServiceImpl implements UserModificationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserHelperService userHelperService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    @Override
    public void edit(UserEditDTO userEditDTO) throws IOException {
        User user = userHelperService.getLoggedUser();
        BeanUtils.copyProperties(userEditDTO, user);
        changeProfilePicture(userEditDTO, user);
        userRepository.save(user);
    }

    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDTO) {
        User user = userHelperService.getLoggedUser();
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
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
        user.setProfilePicture(pictureService.createPictureOrGetDefault(userEditDTO.getProfilePicture(), USERS_PROFILE_PICTURES_FOLDER));
        if (oldprofilePicture.getId() != 1) pictureService.deletePicture(oldprofilePicture);
    }
}
