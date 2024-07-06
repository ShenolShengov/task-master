package bg.softuni.planner.repository;

import bg.softuni.planner.model.entity.Role;
import bg.softuni.planner.model.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(UserRoles userRoles);
}
