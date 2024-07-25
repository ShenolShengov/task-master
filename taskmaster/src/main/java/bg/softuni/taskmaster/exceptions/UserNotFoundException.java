package bg.softuni.taskmaster.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User in not found");
    }
}
