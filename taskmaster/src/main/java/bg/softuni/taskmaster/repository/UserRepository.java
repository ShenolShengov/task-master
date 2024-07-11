package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String value);

    Integer countAllByAge(Integer age);

    List<User> findAllByAge(Integer age, Pageable pageable);

    List<User> findAllByEmailContains(String email, Pageable pageable);

    List<User> findAllByUsernameContainsIgnoreCaseOrFullNameContainsIgnoreCase(String username, String fullName,
                                                                               Pageable pageable);

    Integer countAllByEmailContains(String email);

    Integer countAllByUsernameContainsIgnoreCaseOrFullNameContainsIgnoreCase(String username, String fullName);
}
