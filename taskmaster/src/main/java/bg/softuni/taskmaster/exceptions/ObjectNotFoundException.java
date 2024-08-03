package bg.softuni.taskmaster.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super("Object is not found");
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
