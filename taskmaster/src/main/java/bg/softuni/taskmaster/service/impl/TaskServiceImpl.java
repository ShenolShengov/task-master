package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.exceptions.TaskNotFoundException;
import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.model.enums.TaskPriorities;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.service.TaskHelperService;
import bg.softuni.taskmaster.service.TaskService;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskHelperService taskHelperService;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    @Value("${tasks.retention.period}")
    private Period retentionPeriod;

    @Override
    public void add(TaskAddEditDTO taskAddEditDTO) {
        Task task = modelMapper.map(taskAddEditDTO, Task.class);
        task.setPriority(TaskPriorities.valueOf(taskAddEditDTO.getPriority()));
        task.setUser(userHelperService.getLoggedUser());
        taskRepository.save(task);
    }

    @Override
    public void edit(TaskAddEditDTO taskEditDTO) {
        Task currentTask = taskRepository.findById(taskEditDTO.getId())
                .orElseThrow(TaskNotFoundException::new);
        BeanUtils.copyProperties(taskEditDTO, currentTask);
        currentTask.setPriority(TaskPriorities.valueOf(taskEditDTO.getPriority()));
        taskRepository.save(currentTask);
    }

    @Override
    @Transactional
    public Page<TaskInfoDTO> getFor(LocalDate dueDate, Pageable pageable) {
        return taskRepository.findAllByUserUsernameAndDueDate(userHelperService.getUsername(), dueDate, pageable)
                .map(e -> modelMapper.map(e, TaskInfoDTO.class));
    }

    @Override
    @PreAuthorize("@taskServiceImpl.isActualUser(#id)")
    public TaskInfoDTO getInfo(Long id) {
        return modelMapper.map(taskHelperService.getById(id), TaskInfoDTO.class);
    }

    @Override
    @PreAuthorize("@taskServiceImpl.isActualUser(#id)")
    public void delete(Long id) {
        taskRepository.delete(taskHelperService.getById(id));
    }

    @Override
    public void deleteOldTasks() {
        LocalDate deleteBefore = LocalDate.now().minus(retentionPeriod);
        taskRepository.deleteOldTasks(deleteBefore);
    }

    @Override
    public boolean isActualUser(Long id) {
        return taskHelperService.getById(id).getUser().getUsername().equals(userHelperService.getUsername());
    }
}
