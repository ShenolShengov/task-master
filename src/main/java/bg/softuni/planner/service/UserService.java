package bg.softuni.planner.service;

import bg.softuni.planner.model.dto.UserRegisterDTO;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);
}
