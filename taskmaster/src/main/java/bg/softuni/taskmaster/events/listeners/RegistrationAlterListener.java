package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.RegistrationAlterEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static bg.softuni.taskmaster.model.enums.EmailParam.*;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.USER_REGISTRATION_ALTER;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Component
@RequiredArgsConstructor
public class RegistrationAlterListener {

    private final MailService mailService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

    @EventListener
    public void handleRegistrationAlterEvent(RegistrationAlterEvent event) {
        Payload payload = mailService.createPayload(APP_MAIL, ADMIN_MAIL, REGISTRATION_ALTER_SUBJECT, USER_REGISTRATION_ALTER,
                toParams(USERNAME, event.getUsername(),
                        EMAIL, event.getEmail(),
                        JOINED_TIME, FORMATTER.format(LocalDateTime.now())));
        mailService.send(payload);
    }
}
