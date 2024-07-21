package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AnswerDetailsDTO implements Serializable {

    private Long id;
    private String description;
    private String code;
    private String createdTime;
    private UserAnswerDTO user;
}
