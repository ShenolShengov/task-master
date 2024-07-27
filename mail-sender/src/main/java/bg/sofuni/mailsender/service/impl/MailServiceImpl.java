package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.dto.Payload;
import bg.sofuni.mailsender.dto.enums.EmailParam;
import bg.sofuni.mailsender.dto.enums.EmailTemplate;
import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import bg.sofuni.mailsender.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.util.EnumMap;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final ModelMapper modelMapper;
    private final MailHistoryRepository mailHistoryRepository;

    @Value("${spring.mail.sendEmails}")
    private boolean sendEmails;

    @Override
    public void send(Payload payload) throws MessagingException {
        MimeMessage mimeMessage = getMimeMessage(payload);
        if (sendEmails) {
            mailSender.send(mimeMessage);
        }
        MailHistory mailHistory = modelMapper.map(payload, MailHistory.class);
        mailHistory.setDate(Instant.now());
        mailHistoryRepository.save(mailHistory);
    }

    private MimeMessage getMimeMessage(Payload payload) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(payload.getFrom());
        helper.setTo(payload.getTo());
        helper.setSubject(payload.getSubject());
        helper.setText(generateEmailBody(payload.getTemplate(), payload.getParams()), true);
        return mimeMessage;
    }


    private String generateEmailBody(EmailTemplate emailTemplate, EnumMap<EmailParam, String> params) {
        Context context = new Context();
        params.forEach((k, v) -> context.setVariable(k.name(), v));
        return templateEngine.process(emailTemplate.getEmailTemplateName(), context);
    }
}
