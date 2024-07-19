package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.AuthorizationService;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserHelperService userHelperService;

    @Override
    public void makeAdmin(Long id) {
        User user = userHelperService.getUser(id);
        user.getRoles().add(roleRepository.getByName(UserRoles.ADMIN));
        userRepository.save(user);
    }

    @Override
    public void removeAdmin(Long id) {
        User user = userHelperService.getUser(id);
        user.getRoles().remove(roleRepository.getByName(UserRoles.ADMIN));
        userRepository.save(user);
    }
}
