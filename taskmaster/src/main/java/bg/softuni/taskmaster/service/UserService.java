package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.UserDetailsInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void delete(Long id);

    Page<UserDetailsInfoDTO> getAll(String searchQuery, Pageable pageable);

}
