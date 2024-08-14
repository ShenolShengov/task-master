package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.events.ChangePasswordEvent;
import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserProfileDTO;
import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.PictureService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public void edit(UserProfileDTO userProfileDTO) throws IOException {
        User user = userHelperService.getLoggedUser();
        if (!user.getUsername().equals(userProfileDTO.getUsername())) {
            changeNameInSecurityContext(user.getPassword(), userProfileDTO.getUsername());
        }
        BeanUtils.copyProperties(userProfileDTO, user);
        changeProfilePicture(userProfileDTO, user);
        userRepository.save(user);
    }

    private void changeNameInSecurityContext(String currentPassword, String newUsername) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails updatedPrincipal = org.springframework.security.core.userdetails.User
                .withUsername(newUsername)
                .password(currentPassword)
                .authorities(auth.getAuthorities())
                .build();
        UsernamePasswordAuthenticationToken updatedAuth = new
                UsernamePasswordAuthenticationToken(updatedPrincipal, auth.getCredentials(), auth.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(updatedAuth);
    }

    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDTO) {
        User user = userHelperService.getLoggedUser();
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
        publisher.publishEvent(new ChangePasswordEvent(this, user.getUsername(), user.getEmail()));
    }


    private void changeProfilePicture(UserProfileDTO userProfileDTO, User user) throws IOException {
        if (user.getProfilePicture().getId() == 1 && userProfileDTO.getProfilePicture().isEmpty()) {
            return;
        }
        Picture oldprofilePicture = user.getProfilePicture();
        user.setProfilePicture(pictureService.savePicture(userProfileDTO.getProfilePicture(), USERS_PROFILE_PICTURES_FOLDER));
        userRepository.save(user);
        if (oldprofilePicture.getId() != 1) pictureService.deletePicture(oldprofilePicture);
    }
}
