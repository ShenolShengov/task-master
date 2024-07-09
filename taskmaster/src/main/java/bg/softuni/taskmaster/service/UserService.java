package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.*;

import java.util.Set;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    Set<TaskInfoDTO> getTasks();

    UserInfoDTO getInfo(Long id);

    Set<UserInfoDTO> getAllInfo();

    void edit(UserEditDTO userEditDTO);

    void delete(Long id);

    void makeAdmin(Long id);

    void removeAdmin(Long id);

    void changePassword(UserChangePasswordDTO changePasswordDTO);
}
