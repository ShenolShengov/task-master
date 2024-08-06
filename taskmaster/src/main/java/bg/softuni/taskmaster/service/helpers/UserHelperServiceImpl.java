package bg.softuni.taskmaster.service.helpers;

import bg.softuni.taskmaster.exceptions.UserNotFoundException;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHelperServiceImpl implements UserHelperService {

    private final UserRepository userRepository;

    @Override
    public boolean isAuthenticated() {
        return getAuthentication().getAuthorities()
                .stream().anyMatch(e -> e.getAuthority().equals("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return getAuthentication().getName();
    }

    @Override
    public boolean isAdmin() {
        return getAuthentication().getAuthorities()
                .stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public boolean isAdmin(Long id) {
        return getUser(id).getRoles()
                .stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN));
    }

    @Override
    public User getLoggedUser() {
        return userRepository.findByUsername(getUsername())
                .orElseThrow(UserNotFoundException::new);
    }


    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String[] getAdminsEmails() {
        return userRepository.findAll().stream()
                .filter(e -> e.getRoles().stream().anyMatch(r -> r.getName().equals(UserRoles.ADMIN)))
                .map(User::getEmail)
                .toArray(String[]::new);
    }

}
