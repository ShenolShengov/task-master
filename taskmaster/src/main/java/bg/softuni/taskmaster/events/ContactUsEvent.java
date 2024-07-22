package bg.softuni.taskmaster.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ContactUsEvent extends ApplicationEvent {

    private final String title;
    private final String message;
    private final String userEmail;

    public ContactUsEvent(Object source, String title, String message, String userEmail) {
        super(source);
        this.title = title;
        this.message = message;
        this.userEmail = userEmail;
    }

}
