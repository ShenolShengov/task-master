package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class QuestionDetailsDTO implements Serializable {

    private String id;
    private String title;
    private String description;
    private String code;
    private String createdTime;
    private String userUsername;
    private Set<String> tags;
}
