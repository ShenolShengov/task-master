package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.RegistrationEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static bg.softuni.taskmaster.model.enums.EmailParam.USERNAME;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.USER_REGISTRATION;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Component
@RequiredArgsConstructor
public class RegistrationListener {

    private final EmailService emailService;

    @EventListener
    public void handleRegistration(RegistrationEvent event) {
        Payload payload = emailService.createPayload(APP_MAIL, event.getEmail(), getSubject(event.getUsername()), USER_REGISTRATION,
                toParams(USERNAME, event.getUsername()));
        emailService.send(payload);
    }

    private String getSubject(String username) {
        return String.format(REGISTRATION_SUBJECT, username);
    }
}
