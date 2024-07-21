package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;

import java.io.IOException;

public interface UserModificationService {


    void edit(UserEditDTO userEditDTO) throws IOException;

    UserEditDTO getCurrentUserEditData();

    void changePassword(UserChangePasswordDTO changePasswordDTO);
}
