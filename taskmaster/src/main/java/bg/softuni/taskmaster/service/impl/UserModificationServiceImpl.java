package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.events.ChangePasswordEvent;
import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.PictureService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher publisher;

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
        publisher.publishEvent(new ChangePasswordEvent(this, user.getUsername(), user.getEmail()));
    }

    @Override
    public UserEditDTO getCurrentUserEditData() {
        UserEditDTO userEditDTO = new UserEditDTO();
        User loggedUser = userHelperService.getLoggedUser();
        BeanUtils.copyProperties(loggedUser, userEditDTO);
        return userEditDTO;
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
