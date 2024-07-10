package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    Set<TaskInfoDTO> getTasks();

    UserInfoDTO getInfo(Long id);

    Set<UserInfoDTO> getAllInfo(Pageable pageable);

    void edit(UserEditDTO userEditDTO);

    void delete(Long id);

    void makeAdmin(Long id);

    void removeAdmin(Long id);

    void changePassword(UserChangePasswordDTO changePasswordDTO);

    Set<UserInfoDTO> search(String searchQuery);

}
