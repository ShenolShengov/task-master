package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.AccountDeletionEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailTemplate;
import bg.softuni.taskmaster.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static bg.softuni.taskmaster.model.enums.EmailParam.USERNAME;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Component
@RequiredArgsConstructor
public class AccountDeletionListener {

    private final EmailService emailService;

    @EventListener
    public void handleProfileDeletionEvent(AccountDeletionEvent event) {
        Payload payload = emailService.createPayload(APP_MAIL, event.getEmail(), getSubject(event.getUsername()),
                EmailTemplate.DELETE_ACCOUNT,
                toParams(USERNAME, event.getUsername()));
        emailService.send(payload);
    }

    private String getSubject(String username) {
        return String.format(ACCOUNT_DELETION_SUBJECT, username);
    }
}
