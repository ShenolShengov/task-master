package bg.softuni.taskmaster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Question not found")
public class QuestionNotFoundException extends ObjectNotFoundException {

    public QuestionNotFoundException() {
        super("Question");
    }
}
