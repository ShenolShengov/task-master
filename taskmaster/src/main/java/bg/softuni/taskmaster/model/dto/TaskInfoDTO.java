package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskInfoDTO {

    private String name;
    private String category;
    private String priority;
    private String description;
    private String startTime;
    private String endTime;
    private boolean allDay;
}
