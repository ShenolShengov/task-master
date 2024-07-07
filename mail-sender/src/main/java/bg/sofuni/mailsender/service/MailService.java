package bg.sofuni.mailsender.service;

import bg.sofuni.mailsender.dto.SendMailDTO;

public interface MailService {

    void send(SendMailDTO mailDTO);
}
