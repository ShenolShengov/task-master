package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    UserInfoDTO getInfo(Long id);

    void edit(UserEditDTO userEditDTO);

    void delete(Long id);

    void makeAdmin(Long id);

    void removeAdmin(Long id);

    void changePassword(UserChangePasswordDTO changePasswordDTO);

    Page<UserInfoDTO> getAll(String searchQuery, Pageable pageable);
    UserEditDTO getCurrentUserEditData();
}
