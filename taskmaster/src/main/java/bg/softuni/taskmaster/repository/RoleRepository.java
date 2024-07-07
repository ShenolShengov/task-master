package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(UserRoles userRoles);
}
