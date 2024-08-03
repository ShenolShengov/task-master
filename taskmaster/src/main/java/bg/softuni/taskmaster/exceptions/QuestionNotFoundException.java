package bg.softuni.taskmaster.exceptions;

public class QuestionNotFoundException extends ObjectNotFoundException {

    public QuestionNotFoundException() {
        super("Question is not found");
    }
}
