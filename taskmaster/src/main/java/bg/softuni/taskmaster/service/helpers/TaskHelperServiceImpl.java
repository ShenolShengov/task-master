package bg.softuni.taskmaster.service.helpers;


import bg.softuni.taskmaster.exceptions.TaskNotFoundException;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskHelperServiceImpl implements bg.softuni.taskmaster.service.TaskHelperService {

    private final TaskRepository taskRepository;

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }
}
