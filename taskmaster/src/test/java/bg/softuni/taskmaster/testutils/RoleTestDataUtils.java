package bg.softuni.taskmaster.testutils;

import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleTestDataUtils {

    private final RoleRepository roleRepository;

    public RoleTestDataUtils(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public static Role getTestRole(UserRoles name) {
        return new Role(name);
    }

    public Role getRole(UserRoles name) {
        return roleRepository.getByName(name);
    }

}
