package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.model.enums.TaskPriorities;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.service.TaskService;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

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
                .orElseThrow(NullPointerException::new);
        BeanUtils.copyProperties(taskEditDTO, currentTask);
        taskRepository.save(currentTask);
    }

    @Override
    @Transactional
    public Page<TaskInfoDTO> getTasksFor(LocalDate dueDate, Pageable pageable) {
        return taskRepository.findAllByUserIdAndDueDate(userHelperService.getLoggedUser().getId(), dueDate, pageable)
                .map(e -> modelMapper.map(e, TaskInfoDTO.class));
    }

    @Override
    public TaskInfoDTO getInfo(Long id) {
        return taskRepository
                .findById(id)
                .map(e -> modelMapper.map(e, TaskInfoDTO.class))
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public boolean isActualUser(Long id) {
        return taskRepository.findById(id)
                .filter(e -> e.getUser().getId().equals(userHelperService.getLoggedUser().getId()))
                .isPresent();
    }
}
