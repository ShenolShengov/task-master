package bg.softuni.taskmaster.testutils;


import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

import static bg.softuni.taskmaster.model.enums.TaskPriorities.HIGH;

@Component
public class TaskTestDataUtils {

    private final TaskRepository taskRepository;

    public TaskTestDataUtils(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public static Task getTestTask(User user) {
        return new Task("test",
                "category", HIGH, LocalDate.now(), LocalTime.of(9, 0).withNano(0),
                LocalTime.of(10, 0).withNano(0), false, "desc", user);

    }

    public Task saveTestTask(User user) {
        return taskRepository.save(getTestTask(user));
    }


    public void clearDB() {
        taskRepository.deleteAll();
    }
}
