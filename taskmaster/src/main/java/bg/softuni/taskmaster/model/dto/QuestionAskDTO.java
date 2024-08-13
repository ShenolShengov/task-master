package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.validtags.ValidTags;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Setter
public class QuestionAskDTO implements Serializable {

    @NotNull(message = "{validation.title.length}")
    @Length(min = 5, max = 50, message = "{validation.title.length}")
    private String title;

    @ValidTags
    private String tags;

    @NotNull(message = "{validation.description.length}")
    @Length(min = 5, max = 2000, message = "{validation.description.length}")
    private String description;

    @NotNull(message = "{validation.question.answer.code.length}")
    @Length(max = 5000, message = "{validation.question.answer.code.length}")
    private String code;

}
