package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Period;

public interface TaskService {

    void add(TaskAddEditDTO taskAddEditDTO);

    void edit(TaskAddEditDTO taskEditDTO);

    void delete(Long id);

    void deleteOldTasks();

    Page<TaskInfoDTO> getFor(LocalDate dueDate, Pageable pageable);

    TaskInfoDTO getInfo(Long id);

    boolean isActualUser(Long id);
}
