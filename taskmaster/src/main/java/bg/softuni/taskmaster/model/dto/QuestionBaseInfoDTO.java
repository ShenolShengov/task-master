package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class QuestionBaseInfoDTO implements Serializable {

    private String id;
    private String title;
    private UserAnswerDTO user;
    private String description;
    private String createdTime;
    private Set<String> tags;
    private Integer answers;

    public QuestionBaseInfoDTO() {
        this.tags = new LinkedHashSet<>();
    }

    public QuestionBaseInfoDTO setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }
}
