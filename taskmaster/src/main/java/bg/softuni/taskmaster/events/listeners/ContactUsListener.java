package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.ContactUsEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailParam;
import bg.softuni.taskmaster.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

import static bg.softuni.taskmaster.model.enums.EmailParam.*;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.CONTACT_US;
import static bg.softuni.taskmaster.utils.EmailUtils.APP_MAIL;

@Component
@RequiredArgsConstructor
public class ContactUsListener {

    private final EmailService emailService;

    @EventListener
    public void handleContactUsEvent(ContactUsEvent event) {
        Payload payload = emailService.createPayload(APP_MAIL, APP_MAIL, "Contact us email",
                CONTACT_US, getParams(event));
        emailService.send(payload);
    }

    private EnumMap<EmailParam, String> getParams(ContactUsEvent event) {
        return new EnumMap<>(Map.of(EMAIL, event.getUserEmail(), MESSAGE, event.getMessage(), TITLE, event.getTitle()));
    }
}
