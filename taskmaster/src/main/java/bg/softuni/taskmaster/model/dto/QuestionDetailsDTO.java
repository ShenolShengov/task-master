package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
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
    private Set<AnswerDetailsDTO> answers;

    public QuestionDetailsDTO() {
        this.tags = new LinkedHashSet<>();
        this.answers = new LinkedHashSet<>();
    }
}
