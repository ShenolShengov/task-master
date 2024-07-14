package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.*;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.QuestionService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;
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
    @Transactional
    public Page<TaskInfoDTO> getTasksFor(LocalDate dueDate, Pageable pageable) {
        return taskRepository.findAllByUserIdAndDueDate(userHelperService.getUser().getId(), dueDate, pageable)
                .map(e -> modelMapper.map(e, TaskInfoDTO.class));
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
    public Page<UserInfoDTO> getAllInfo(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toInfo);
    }

    @Override
    public void edit(UserEditDTO userEditDTO) {
        User user = userHelperService.getUser();
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
        User user = userHelperService.getUser();
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }


    @Override
    public Page<UserInfoDTO> search(String searchQuery, Pageable pageable) {
        if (searchQuery.isEmpty()) {
            return userRepository.findAll(pageable).map(this::toInfo);
        }
        return userRepository.findAllBySearchQuery(searchQuery, pageable).map(this::toInfo);
    }

    @Override
    public Page<QuestionBaseInfoDTO> getQuestionsFrom(LocalDate questionCreatedTime, Pageable pageable) {
        Long userId = userHelperService.getUser().getId();
        if (questionCreatedTime == null) {
            return questionRepository.findAllByUserId(userId, pageable)
                    .map(questionService::getBaseInfoDTO);
        }

        return questionRepository.findAllByUserIdAndCreatedTimeDate(userId, questionCreatedTime, pageable)
                .map(questionService::getBaseInfoDTO);
    }

    @Override
    public UserEditDTO getCurrentUserEditData() {
        return modelMapper.map(userHelperService.getUser(), UserEditDTO.class);
    }
}
