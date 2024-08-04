package bg.softuni.taskmaster.exceptions;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {

    private final String type;

    public ObjectNotFoundException(String type) {
        super(type + " is not found!");
        this.type = type;
    }
}
