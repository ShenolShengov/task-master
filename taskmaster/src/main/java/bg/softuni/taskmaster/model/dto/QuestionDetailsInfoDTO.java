package bg.softuni.taskmaster.model.dto;

import jakarta.persistence.OrderBy;
import lombok.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class QuestionDetailsInfoDTO implements Serializable {

    private String id;
    private String title;
    private String description;
    private String code;
    private String createdTime;
    private String userUsername;
    private Set<String> tags;

    private Set<AnswerDetailsDTO> answers;

    public QuestionDetailsInfoDTO() {
        this.tags = new LinkedHashSet<>();
        this.answers = new LinkedHashSet<>();
    }

    public QuestionDetailsInfoDTO setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }
}
