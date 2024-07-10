package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.*;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.getByName(UserRoles.USER));
        userRepository.save(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public Set<TaskInfoDTO> getTasks() {
        return userHelperService.getUser().getTasks()
                .stream()
                .map(e -> modelMapper.map(e, TaskInfoDTO.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public UserInfoDTO getInfo(Long id) {
        return modelMapper.map(userHelperService.getUser(id), UserInfoDTO.class);
    }

    private UserInfoDTO toInfo(User e) {
        UserInfoDTO infoDTO = modelMapper.map(e, UserInfoDTO.class);
        infoDTO.setAdmin(userHelperService.isAdmin(e));
        return infoDTO;
    }

    @Override
    public Set<UserInfoDTO> getAllInfo(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream().map(this::toInfo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void edit(UserEditDTO userEditDTO) {
        User user = userHelperService.getUser(userEditDTO.getId());
        BeanUtils.copyProperties(userEditDTO, user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void makeAdmin(Long id) {
        User user = userHelperService.getUser(id);
        user.getRoles().add(roleRepository.getByName(UserRoles.ADMIN));
        userRepository.save(user);
    }

    @Override
    public void removeAdmin(Long id) {
        User user = userHelperService.getUser(id);
        user.getRoles().remove(roleRepository.getByName(UserRoles.ADMIN));
        userRepository.save(user);
    }

    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDTO) {
        User user = userHelperService.getUser(changePasswordDTO.getId());
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public Set<UserInfoDTO> search(String searchQuery) {

        return userRepository.findAll()
                .stream()
                .filter(e ->
                       (isNumber(searchQuery) && e.getAge().equals(Integer.valueOf(searchQuery))) ||
                       ("@".contains(searchQuery) && e.getEmail().contains(searchQuery)) ||
                       (!isNumber(searchQuery) && e.getUsername().contains(searchQuery)) ||
                       (!isNumber(searchQuery) && e.getFullName().contains(searchQuery))
                )
                .map(this::toInfo)
                .collect(Collectors.toCollection(LinkedHashSet::new));

//        Set<User> founded = switch (by) {
//            case "username" -> userRepository.findAllByUsernameContains(value, pageable);
//            case "fullName" -> userRepository.findAllByFullNameContains(value, pageable);
//            case "email" -> userRepository.findAllBEmailContains(value, pageable);
//            case "age" -> userRepository.findAllByAgeContains(Integer.valueOf(value), pageable);
//            default -> new HashSet<>();
//        };
//        return founded
//                .stream().map(this::toInfo)
//                .collect(Collectors.toCollection(LinkedHashSet::new));
    }



    private boolean isNumber(String searchQuery) {
        //todo fix
        try {
            Integer.valueOf(searchQuery);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
