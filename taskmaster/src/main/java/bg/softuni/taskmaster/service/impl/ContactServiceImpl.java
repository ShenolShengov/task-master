package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.service.ContactService;
import bg.softuni.taskmaster.service.MailService;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static bg.softuni.taskmaster.model.enums.EmailParam.EMAIL;
import static bg.softuni.taskmaster.model.enums.EmailParam.MESSAGE;
import static bg.softuni.taskmaster.model.enums.EmailTemplate.CONTACT_US;
import static bg.softuni.taskmaster.utils.EmailUtils.*;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final MailService mailService;
    private final UserHelperService userHelperService;

    @Override
    public void contactUs(ContactUsDTO contactUsDTO) {
        Payload payload = mailService.createPayload(APP_MAIL, getSubject(contactUsDTO.getTitle()), CONTACT_US,
                toParams(EMAIL, contactUsDTO.getEmail(), MESSAGE, contactUsDTO.getMessage()), APP_MAIL);
        mailService.send(payload);
    }

    @Override
    public ContactUsDTO getContactUs() {
        ContactUsDTO contactUsDTO = new ContactUsDTO();
        if (userHelperService.isAuthenticated()) {
            contactUsDTO.setEmail(userHelperService.getEmail());
        }
        return contactUsDTO;
    }

    private static String getSubject(String title) {
        return String.format(CONTACT_US_SUBJECT, title);
    }
}
