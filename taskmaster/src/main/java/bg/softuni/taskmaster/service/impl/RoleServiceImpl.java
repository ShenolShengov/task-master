package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
