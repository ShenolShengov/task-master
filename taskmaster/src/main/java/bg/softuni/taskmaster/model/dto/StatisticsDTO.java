package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StatisticsDTO implements Serializable {

    private long usersCount;
    private long adminUsersCount;
    private long regularUsersCount;
    private long tasksCount;
    private long questionsCount;
    private long answersCount;

}
