package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class QuestionDetailsInfoDTO implements Serializable {

    private String id;
    private String title;
    private String description;
    private String code;
    private String createdTime;
    private UserAnswerDTO user;
    private Set<String> tags;
    private List<AnswerDetailsDTO> answers;

    public QuestionDetailsInfoDTO() {
        this.tags = new LinkedHashSet<>();
        this.answers = new ArrayList<>();
    }

    public QuestionDetailsInfoDTO setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }
}
