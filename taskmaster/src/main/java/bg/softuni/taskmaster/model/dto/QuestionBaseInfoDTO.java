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
    private String description;
    private String createdTime;
    private Set<String> tags;

    public QuestionBaseInfoDTO() {
        this.tags = new LinkedHashSet<>();
    }
}
