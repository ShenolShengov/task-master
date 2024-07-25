package bg.sofuni.mailsender.service;

import bg.sofuni.mailsender.dto.Payload;
import jakarta.mail.MessagingException;

public interface MailService {

    void send(Payload payload) throws MessagingException;

}
