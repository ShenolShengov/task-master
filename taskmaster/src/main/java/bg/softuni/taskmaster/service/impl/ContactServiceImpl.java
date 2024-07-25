package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.ContactService;
import bg.softuni.taskmaster.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static bg.softuni.taskmaster.model.enums.EmailParam.EMAIL;
import static bg.softuni.taskmaster.model.enums.EmailParam.MESSAGE;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.CONTACT_US;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final EmailService emailService;

    @Override
    public void contactUs(ContactUsDTO contactUsDTO) {
        Payload payload = emailService.createPayload(APP_MAIL, APP_MAIL, getSubject(contactUsDTO.getTitle())
                , CONTACT_US, toParams(EMAIL, contactUsDTO.getEmail(), MESSAGE, contactUsDTO.getMessage()));
        emailService.send(payload);
    }

    private static String getSubject(String title) {
        return String.format(CONTACT_US_SUBJECT, title);
    }
}
