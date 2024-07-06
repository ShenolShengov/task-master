package bg.softuni.planner.service.impl;

import bg.softuni.planner.model.entity.Role;
import bg.softuni.planner.model.entity.User;
import bg.softuni.planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not fount"));
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(toGrantedAuthority(user.getRoles()))
                .build();
    }

    private List<GrantedAuthority> toGrantedAuthority(Set<Role> roles) {
        return roles
                .stream().map(e -> new SimpleGrantedAuthority("ROLE_" + e.getName().name()))
                .collect(Collectors.toList());
    }
}
