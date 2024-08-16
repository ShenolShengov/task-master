package bg.softuni.taskmaster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Answer not found")
public class AnswerNotFoundException extends ObjectNotFoundException {

    public AnswerNotFoundException() {
        super("Answer");
    }
}
