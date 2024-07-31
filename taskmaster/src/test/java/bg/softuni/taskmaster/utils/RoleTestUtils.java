package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleTestUtils {

    private static RoleRepository roleRepository;


    public static Role getRole(UserRoles name, boolean fromDB) {
        if (fromDB) {
            return roleRepository.getByName(name);
        }
        return new Role(name);
    }

    public static void setRoleRepository(RoleRepository roleRepository) {
        RoleTestUtils.roleRepository = roleRepository;
    }
}
