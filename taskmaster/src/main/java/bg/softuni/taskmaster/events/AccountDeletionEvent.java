package bg.softuni.taskmaster.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AccountDeletionEvent extends ApplicationEvent {

    public final String username;
    private final String email;

    public AccountDeletionEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }
}
