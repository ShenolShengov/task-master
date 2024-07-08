package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;

import java.util.Set;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    Set<TaskInfoDTO> getTasks();
}
