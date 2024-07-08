package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;

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
}
