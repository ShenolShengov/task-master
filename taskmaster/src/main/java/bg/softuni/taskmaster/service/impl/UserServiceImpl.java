package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
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
    public UserInfoDTO getInfo(String username) {
        return userRepository.findByUsername(username)
                .map(this::toInfo)
                .orElseThrow(RuntimeException::new);//todo add custom exception ObjectNotFoundException
    }

    private UserInfoDTO toInfo(User e) {
        UserInfoDTO infoDTO = modelMapper.map(e, UserInfoDTO.class);
        infoDTO.setAdmin(userHelperService.isAdmin(e));
        return infoDTO;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Set<UserInfoDTO> getAllInfo() {
        return userRepository.findAll()
                .stream().map(this::toInfo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void edit(UserEditDTO userEditDTO) {
        User user = userRepository.findById(userEditDTO.getId())
                .orElseThrow(RuntimeException::new);
        BeanUtils.copyProperties(userEditDTO, user);
        userRepository.save(user);
    }
}
