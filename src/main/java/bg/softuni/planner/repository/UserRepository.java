package bg.softuni.planner.repository;

import bg.softuni.planner.model.entity.Comment;
import bg.softuni.planner.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
