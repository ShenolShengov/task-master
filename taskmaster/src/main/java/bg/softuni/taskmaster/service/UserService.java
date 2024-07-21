package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void delete(Long id);

    UserInfoDTO getInfo(Long id);

    Page<UserInfoDTO> getAll(String searchQuery, Pageable pageable);
}
