package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.User;
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
        return haseRole("USER");
    }

    @Override
    public String getName() {
        return getAuthentication().getName();
    }

    @Override
    public boolean haseRole(String role) {
        return haseRole(role, getLoggedUser().getId());
    }

    @Override
    public boolean haseRole(String role, Long id) {
        return getUser(id).getRoles()
                .stream().anyMatch(e -> e.getName().name().equals(role));
    }

    @Override
    public boolean isAdmin() {
        return haseRole("ADMIN");
    }

    @Override
    public boolean isAdmin(Long id) {
        return haseRole("ADMIN", id);
    }

    @Override
    public User getLoggedUser() {
        return userRepository.findByUsername(getName())
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
