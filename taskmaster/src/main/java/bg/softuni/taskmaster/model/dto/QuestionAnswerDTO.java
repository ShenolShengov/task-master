package bg.softuni.taskmaster.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Setter
public class QuestionAnswerDTO implements Serializable {

    @NotNull(message = "{validation.description.length}")
    @Length(min = 5, max = 2000, message = "{{validation.description.length}}")
    private String description;

    @NotNull(message = "{validation.question.answer.code.length}")
    @Length(max = 5000, message = "{validation.question.answer.code.length}")
    private String code;
}
