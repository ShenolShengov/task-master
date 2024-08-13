package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserRegisterDTO;

import java.io.IOException;

public interface AuthenticationService {

    void register(UserRegisterDTO userRegisterDTO) throws IOException;

}
