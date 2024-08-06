package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.RegistrationEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static bg.softuni.taskmaster.model.enums.EmailParam.USERNAME;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.USER_REGISTRATION;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Component
@RequiredArgsConstructor
public class RegistrationListener {

    private final MailService mailService;

    @EventListener
    public void handleRegistration(RegistrationEvent event) {
        Payload payload = mailService.createPayload(APP_MAIL, getSubject(event.getUsername()), USER_REGISTRATION,
                toParams(USERNAME, event.getUsername()), event.getEmail());
        mailService.send(payload);
    }

    private String getSubject(String username) {
        return String.format(REGISTRATION_SUBJECT, username);
    }
}
