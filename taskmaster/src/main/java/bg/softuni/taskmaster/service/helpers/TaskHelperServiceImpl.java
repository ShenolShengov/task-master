package bg.softuni.taskmaster.service.helpers;


import bg.softuni.taskmaster.exceptions.TaskNotFoundException;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskHelperServiceImpl implements bg.softuni.taskmaster.service.TaskHelperService {

    private final TaskRepository taskRepository;
    private final UserHelperService userHelperService;

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public boolean isActualUser(Long id) {
        return getById(id).getUser().getUsername().equals(userHelperService.getUsername());
    }

}
