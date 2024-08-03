package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserRegisterDTO;

import java.io.IOException;

public interface UserAuthenticationService {

    void register(UserRegisterDTO userRegisterDTO) throws IOException;

}
