package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.TaskAddDTO;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.model.enums.TaskPriorities;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public void add(TaskAddDTO taskAddDTO) {
        String authenticatedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = modelMapper.map(taskAddDTO, Task.class);
        task.setPriority(TaskPriorities.valueOf(taskAddDTO.getPriority()));
        task.setUser(userRepository.findByUsername(authenticatedUserUsername).orElseThrow(
                RuntimeException::new));//todo add custom exception - ObjectNotFoundException
        taskRepository.save(task);
    }
}
