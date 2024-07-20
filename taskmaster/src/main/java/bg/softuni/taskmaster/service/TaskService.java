package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import bg.softuni.taskmaster.model.dto.TaskInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TaskService {

    void add(TaskAddEditDTO taskAddEditDTO);

    void edit(TaskAddEditDTO taskEditDTO);
    Page<TaskInfoDTO> getTasksFor(LocalDate dueDate, Pageable pageable);

    TaskInfoDTO getInfo(Long id);

}
