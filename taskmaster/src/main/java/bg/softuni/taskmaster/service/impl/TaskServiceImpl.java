package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.TaskAddDTO;
import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.model.enums.TaskPriorities;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.TaskService;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    @Override
    public void add(TaskAddDTO taskAddDTO) {
        Task task = modelMapper.map(taskAddDTO, Task.class);
        task.setPriority(TaskPriorities.valueOf(taskAddDTO.getPriority()));
        task.setUser(userHelperService.getUser());
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public Page<TaskInfoDTO> getTasksFor(LocalDate dueDate, Pageable pageable) {
        return taskRepository.findAllByUserIdAndDueDate(userHelperService.getUser().getId(), dueDate, pageable)
                .map(e -> modelMapper.map(e, TaskInfoDTO.class));
    }
}
