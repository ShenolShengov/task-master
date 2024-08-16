package bg.softuni.taskmaster.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Object not found")
public class ObjectNotFoundException extends RuntimeException {

    private final String type;

    public ObjectNotFoundException(String type) {
        super(type + " is not found!");
        this.type = type;
    }
}
