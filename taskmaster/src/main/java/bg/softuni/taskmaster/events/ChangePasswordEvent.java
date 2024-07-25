package bg.softuni.taskmaster.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChangePasswordEvent extends ApplicationEvent {

    private final String username;
    private final String email;

    public ChangePasswordEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }
}
