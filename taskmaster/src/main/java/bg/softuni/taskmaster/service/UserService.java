package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO) throws IOException;

    UserInfoDTO getInfo(Long id);

    void edit(UserEditDTO userEditDTO) throws IOException;

    void delete(Long id);

    void changePassword(UserChangePasswordDTO changePasswordDTO);

    Page<UserInfoDTO> getAll(String searchQuery, Pageable pageable);
    UserEditDTO getCurrentUserEditData();
}
