package bg.softuni.planner.service.impl;

import bg.softuni.planner.model.dto.UserRegisterDTO;
import bg.softuni.planner.model.entity.User;
import bg.softuni.planner.model.enums.UserRoles;
import bg.softuni.planner.repository.RoleRepository;
import bg.softuni.planner.repository.UserRepository;
import bg.softuni.planner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.getByName(UserRoles.USER));
        userRepository.save(user);
    }
}
