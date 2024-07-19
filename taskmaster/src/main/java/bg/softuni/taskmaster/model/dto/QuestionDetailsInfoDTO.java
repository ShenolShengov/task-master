package bg.softuni.taskmaster.model.dto;

import jakarta.persistence.OrderBy;
import lombok.*;

import java.io.Serializable;
import java.util.*;

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
