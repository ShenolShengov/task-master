package bg.softuni.taskmaster.exceptions;

public class AnswerNotFoundException extends ObjectNotFoundException {

    public AnswerNotFoundException() {
        super("Answer in not found");
    }
}
