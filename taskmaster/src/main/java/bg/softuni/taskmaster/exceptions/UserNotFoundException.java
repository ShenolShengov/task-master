package bg.softuni.taskmaster.exceptions;

public class UserNotFoundException extends ObjectNotFoundException {

    public UserNotFoundException() {
        super("User in not found");
    }
}
