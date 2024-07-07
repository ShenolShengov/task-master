package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.config.MailConfig;
import bg.sofuni.mailsender.dto.SendMailDTO;
import bg.sofuni.mailsender.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;

    @Override
    public void send(SendMailDTO mailDTO) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailConfig.getFrom());
        mail.setTo(mailDTO.getEmail());
        mail.setSubject(mailDTO.getTitle());
        mail.setText(mailDTO.getMessage());
        mailSender.send(mail);
    }
}
