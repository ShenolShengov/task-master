package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.validstartandendtime.ProperStartAndEndTime;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ProperStartAndEndTime
public class TaskAddDTO implements Serializable {

    @NotNull(message = "{validation.task.name.length}")
    @Length(min = 5, max = 20, message = "{validation.task.name.length}")
    private String name;

    @NotNull(message = "{validation.task.category.length}")
    @Length(min = 5, max = 20, message = "{validation.task.category.length}")
    private String category;

    @NotEmpty(message = "{validation.task.priority.not.null}")
    private String priority;

    @NotNull(message = "{validation.task.dueDate.not.null}")
    @FutureOrPresent(message = "{validation.task.dueDate.future.or.present}")
    private LocalDate dueDate;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "{validation.task.time.not.null}")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "{validation.task.time.not.null}")
    private LocalTime endTime;

    private boolean allDay;

    @NotNull(message = "{validation.description.length}")
    @Length(min = 5, max = 2000, message = "{validation.description.length}")
    private String description;


}
