package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.*;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.PagingAndSortingService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.getByName(UserRoles.USER));
        userRepository.save(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public Set<TaskInfoDTO> getTasks() {
        return userHelperService.getUser().getTasks()
                .stream()
                .map(e -> modelMapper.map(e, TaskInfoDTO.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public UserInfoDTO getInfo(Long id) {
        return modelMapper.map(userHelperService.getUser(id), UserInfoDTO.class);
    }

    private UserInfoDTO toInfo(User e) {
        UserInfoDTO infoDTO = modelMapper.map(e, UserInfoDTO.class);
        infoDTO.setAdmin(userHelperService.isAdmin(e));
        return infoDTO;
    }

    @Override
    public Set<UserInfoDTO> getAllInfo(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream().map(this::toInfo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void edit(UserEditDTO userEditDTO) {
        User user = userHelperService.getUser(userEditDTO.getId());
        BeanUtils.copyProperties(userEditDTO, user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
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

    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDTO) {
        User user = userHelperService.getUser(changePasswordDTO.getId());
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public Set<UserInfoDTO> search(String searchQuery, PagingAndSortingService<User> pagingAndSortingService) {
        List<User> founded;
        Integer totalElements;
        Pageable pageable = pagingAndSortingService.getPageable();
        if (isNumber(searchQuery)) {
            Integer age = Integer.valueOf(searchQuery);
            founded = userRepository.findAllByAge(age, pageable);
            totalElements = userRepository.countAllByAge(age);
        } else if ("@".contains(searchQuery)) {
            founded = userRepository.findAllByEmailContains(searchQuery, pageable);
            totalElements = userRepository.countAllByEmailContains(searchQuery);
        } else {
            founded = userRepository
                    .findAllByUsernameContainsIgnoreCaseOrFullNameContainsIgnoreCase(searchQuery, searchQuery,
                            pageable);
            totalElements = userRepository
                    .countAllByUsernameContainsIgnoreCaseOrFullNameContainsIgnoreCase(searchQuery, searchQuery);
        }
        pagingAndSortingService.setTotalElements(totalElements);
        return founded.stream().map(this::toInfo).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Map.Entry<Integer, List<UserInfoDTO>> search(String searchQuery, Pageable pageable) {
        List<User> founded;
        Integer totalElements;
        if (isNumber(searchQuery)) {
            Integer age = Integer.valueOf(searchQuery);
            founded = userRepository.findAllByAge(age, pageable);
            totalElements = userRepository.countAllByAge(age);
        } else if ("@".contains(searchQuery)) {
            founded = userRepository.findAllByEmailContains(searchQuery, pageable);
            totalElements = userRepository.countAllByEmailContains(searchQuery);
        } else {
            founded = userRepository
                    .findAllByUsernameContainsIgnoreCaseOrFullNameContainsIgnoreCase(searchQuery, searchQuery,
                            pageable);
            totalElements = userRepository
                    .countAllByUsernameContainsIgnoreCaseOrFullNameContainsIgnoreCase(searchQuery, searchQuery);
        }
        return Map.entry(totalElements,
                founded.stream().map(this::toInfo).toList());
    }


    private boolean isNumber(String searchQuery) {
        return Arrays.stream(searchQuery.split(""))
                .allMatch(e -> Character.isDigit(e.charAt(0)));

    }
}
