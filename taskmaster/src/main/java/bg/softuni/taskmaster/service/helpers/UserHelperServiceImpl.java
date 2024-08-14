package bg.softuni.taskmaster.service.helpers;

import bg.softuni.taskmaster.exceptions.UserNotFoundException;
import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.entity.TaskMasterUserDetails;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
    public String getEmail() {
        return getLoggedUser().getEmail();
    }

    @Override
    public String getProfilePictureUrl() {
        UserDetails userDetails = (UserDetails) getAuthentication().getPrincipal();
        if (userDetails instanceof TaskMasterUserDetails taskMasterUserDetails) {
            return taskMasterUserDetails.getProfilePictureUrl();
        } else {
            return getLoggedUser().getProfilePicture().getUrl();
        }
    }

    @Override
    public UserDetails toUserDetails(User user) {
        return new TaskMasterUserDetails(
                user.getUsername(), user.getPassword(),
                toGrantedAuthority(user.getRoles()),
                user.getProfilePicture().getUrl()
        );
    }

    private Set<GrantedAuthority> toGrantedAuthority(Set<Role> roles) {
        return roles
                .stream().map(e -> new SimpleGrantedAuthority("ROLE_" + e.getName().name()))
                .collect(Collectors.toSet());
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
