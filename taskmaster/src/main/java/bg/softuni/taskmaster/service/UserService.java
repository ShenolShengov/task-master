package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserRegisterDTO;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);
}
