package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserProfileDTO;

import java.io.IOException;

public interface UserModificationService {


    void edit(UserProfileDTO userProfileDTO) throws IOException;

    UserProfileDTO getLoggedUserProfileDTO();

    void changePassword(UserChangePasswordDTO changePasswordDTO);
}
