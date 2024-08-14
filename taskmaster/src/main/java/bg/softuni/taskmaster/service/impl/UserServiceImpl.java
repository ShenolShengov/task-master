package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.events.AccountDeletionEvent;
import bg.softuni.taskmaster.model.dto.UserDetailsInfoDTO;
import bg.softuni.taskmaster.model.dto.UserProfileDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final ApplicationEventPublisher publisher;


    private UserDetailsInfoDTO toDetailsInfo(User e) {
        UserDetailsInfoDTO userDetailsInfoDTO = modelMapper.map(e, UserDetailsInfoDTO.class);
        userDetailsInfoDTO.setAdmin(userHelperService.isAdmin(e.getId()));
        return userDetailsInfoDTO;
    }

    @Override
    @Transactional
    @PreAuthorize("@userHelperServiceImpl.getLoggedUser().id.equals(#id)  ||  hasRole('ADMIN')")
    public void delete(Long id) {
        User user = userHelperService.getUser(id);
        userRepository.delete(user);
        publisher.publishEvent(new AccountDeletionEvent(this, user.getUsername(), user.getEmail()));
    }

    @Override
    public UserProfileDTO getLoggedUserProfile() {
        return modelMapper.map(userHelperService.getLoggedUser(), UserProfileDTO.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDetailsInfoDTO> getAll(String searchQuery, Pageable pageable) {
        return userRepository.findAllBySearchQuery(searchQuery, pageable).map(this::toDetailsInfo);
    }
}

