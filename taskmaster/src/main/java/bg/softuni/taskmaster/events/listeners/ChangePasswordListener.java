package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.ChangePasswordEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static bg.softuni.taskmaster.model.enums.EmailParam.USERNAME;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.CHANGE_PASSWORD;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Component
@RequiredArgsConstructor
public class ChangePasswordListener {

    private final EmailService emailService;

    @EventListener
    public void handleChangePasswordEvent(ChangePasswordEvent event) {
        Payload payload = emailService.createPayload(APP_MAIL, event.getEmail(), CHANGE_PASSWORD_SUBJECT, CHANGE_PASSWORD,
                toParams(USERNAME, event.getUsername()));
        emailService.send(payload);
    }
}