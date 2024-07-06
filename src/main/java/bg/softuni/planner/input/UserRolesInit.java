package bg.softuni.planner.input;

import bg.softuni.planner.service.RoleService;
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
