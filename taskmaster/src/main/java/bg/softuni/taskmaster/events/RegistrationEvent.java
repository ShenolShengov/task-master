package bg.softuni.taskmaster.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrationEvent extends ApplicationEvent {

    private final String username;
    private final String email;

    public RegistrationEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }
}
