package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.events.RegistrationAlterEvent;
import bg.softuni.taskmaster.events.RegistrationEvent;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.AuthenticationService;
import bg.softuni.taskmaster.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static bg.softuni.taskmaster.utils.PictureUtils.USERS_PROFILE_PICTURES_FOLDER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PictureService pictureService;
    private final ApplicationEventPublisher publisher;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) throws IOException {
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.getRoles().add(roleRepository.getByName(UserRoles.USER));
        MultipartFile profilePicture = userRegisterDTO.getProfilePicture();
        user.setProfilePicture(pictureService.createPictureOrGetDefault(profilePicture, USERS_PROFILE_PICTURES_FOLDER));
        userRepository.save(user);
        publisher.publishEvent(new RegistrationEvent(this, userRegisterDTO.getUsername(),
                userRegisterDTO.getEmail()));
        publisher.publishEvent(new RegistrationAlterEvent(this, userRegisterDTO.getUsername(),
                userRegisterDTO.getEmail()));
    }
}
