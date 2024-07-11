package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.*;
import bg.softuni.taskmaster.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    Set<TaskInfoDTO> getTasks();

    UserInfoDTO getInfo(Long id);

    Page<UserInfoDTO> getAllInfo(Pageable pageable);

    void edit(UserEditDTO userEditDTO);

    void delete(Long id);

    void makeAdmin(Long id);

    void removeAdmin(Long id);

    void changePassword(UserChangePasswordDTO changePasswordDTO);

    Page<UserInfoDTO> search(String searchQuery, Pageable pageable);

}
