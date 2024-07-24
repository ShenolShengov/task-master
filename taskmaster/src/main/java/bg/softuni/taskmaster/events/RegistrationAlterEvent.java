package bg.softuni.taskmaster.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrationAlterEvent extends ApplicationEvent {

    private final String username;
    private final String email;

    public RegistrationAlterEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }
}
