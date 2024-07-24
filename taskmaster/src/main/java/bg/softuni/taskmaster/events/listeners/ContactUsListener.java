package bg.softuni.taskmaster.events.listeners;

import bg.softuni.taskmaster.events.ContactUsEvent;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static bg.softuni.taskmaster.model.enums.EmailParam.EMAIL;
import static bg.softuni.taskmaster.model.enums.EmailParam.MESSAGE;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.CONTACT_US;
import static bg.softuni.taskmaster.utils.EmailUtils.APP_MAIL;
import static bg.softuni.taskmaster.utils.EmailUtils.toParams;

@Component
@RequiredArgsConstructor
public class ContactUsListener {

    private final EmailService emailService;

    @EventListener
    public void handleContactUsEvent(ContactUsEvent event) {
        Payload payload = emailService.createPayload(APP_MAIL, APP_MAIL, "[Contact us] - " + event.getTitle(),
                CONTACT_US, toParams(EMAIL, event.getUserEmail(), MESSAGE, event.getMessage()));
        emailService.send(payload);
    }
}
