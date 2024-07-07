package bg.softuni.taskmaster.init;

import bg.softuni.taskmaster.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRolesInit implements CommandLineRunner {

    private final RoleService roleService;

    @Override
    public void run(String... args) {
        if (!roleService.hasInitializedRoles()) roleService.initializeRole();
    }
}
