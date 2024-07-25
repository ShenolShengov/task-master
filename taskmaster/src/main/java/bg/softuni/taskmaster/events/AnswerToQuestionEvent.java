package bg.softuni.taskmaster.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AnswerToQuestionEvent extends ApplicationEvent {

    private final String questionerEmail;
    private final String questionerUsername;
    private final String questionName;
    private final String questionLink;
    private final String answererUsername;

    public AnswerToQuestionEvent(Object source, String questionerEmail, String questionerUsername, String questionName,
                                 String questionLink, String answererUsername) {
        super(source);
        this.questionerEmail = questionerEmail;
        this.questionerUsername = questionerUsername;
        this.questionName = questionName;
        this.questionLink = questionLink;
        this.answererUsername = answererUsername;
    }
}
