package bg.softuni.taskmaster.exceptions;

public class TaskNotFoundException extends ObjectNotFoundException {

    public TaskNotFoundException() {
        super("Task in not found");
    }
}
