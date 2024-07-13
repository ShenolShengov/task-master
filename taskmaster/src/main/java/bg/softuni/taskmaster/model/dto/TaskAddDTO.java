package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.validstartandendtime.ValidStartAndEndTime;
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
@ValidStartAndEndTime
public class TaskAddDTO implements Serializable {

    @NotNull
    @Length(min = 5, max = 20)
    private String name;

    @Length(min = 5, max = 20)
    private String category;

    @NotEmpty
    private String priority;

    @NotNull
    @FutureOrPresent
    private LocalDate dueDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull
    private boolean allDay;

    @NotNull
    @Length(min = 5, max = 2000)
    private String description;


}
