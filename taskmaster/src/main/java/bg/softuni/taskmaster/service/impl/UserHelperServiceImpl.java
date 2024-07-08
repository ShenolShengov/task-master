package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.websocket.AuthenticationException;
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
        return getAuthentication().getAuthorities()
                .stream().anyMatch(e -> e.getAuthority().equals("ROLE_" + role));
    }

    @Override
    @SneakyThrows
    public User getUser()  {
        return userRepository.findByUsername(getName())
                .orElseThrow(() -> new AuthenticationException("User is not authenticated"));
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
