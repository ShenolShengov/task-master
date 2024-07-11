package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String value);


//    Page<User> findAllByAge(Integer age, Pageable pageable);
//
//    Page<User> findAllByEmailContains(String email, Pageable pageable);

    @Query("SELECT u FROM User u WHERE  " +
           "u.username like CONCAT('%',:searchQuery,'%') OR " +
           "u.email like CONCAT('%',:searchQuery,'%') OR " +
           "u.fullName like CONCAT('%',:searchQuery,'%') OR " +
           "u.age = :searchQuery")
    Page<User> findAllBySearchQuery(String searchQuery,
                                    Pageable pageable);


}
