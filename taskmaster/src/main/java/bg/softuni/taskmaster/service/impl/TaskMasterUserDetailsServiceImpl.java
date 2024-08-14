package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMasterUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserHelperService userHelperService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userHelperService::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));
    }
}
