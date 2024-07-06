package bg.softuni.planner.service.impl;

import bg.softuni.planner.model.entity.Role;
import bg.softuni.planner.model.enums.UserRoles;
import bg.softuni.planner.repository.RoleRepository;
import bg.softuni.planner.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public boolean hasInitializedRoles() {
        return roleRepository.count() > 0;
    }

    @Override
    public void initializeRole() {
        Arrays.stream(UserRoles.values())
                .map(Role::new)
                .forEach(roleRepository::save);
    }
}
