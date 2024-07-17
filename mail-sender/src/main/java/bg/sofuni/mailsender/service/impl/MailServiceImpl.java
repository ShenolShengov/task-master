package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.config.MailConfig;
import bg.sofuni.mailsender.dto.EmailParam;
import bg.sofuni.mailsender.dto.EmailTemplate;
import bg.sofuni.mailsender.dto.Payload;
import bg.sofuni.mailsender.dto.SendMailDTO;
import bg.sofuni.mailsender.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.EnumMap;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;


    @Override
    public void send(Payload payload) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(payload.getFrom());
        helper.setTo(payload.getTo());
        helper.setSubject(payload.getSubject());
        helper.setText(generateEmailBody(payload.getTemplate(), payload.getParams()), true);
        mailSender.send(mimeMessage);
    }


    private String generateEmailBody(EmailTemplate emailTemplate, EnumMap<EmailParam, String> params) {
        Context context = new Context();
        params.forEach((k, v) -> context.setVariable(k.name(), v));
        return templateEngine.process(emailTemplate.getEmailTemplateName(), context);
    }
}
