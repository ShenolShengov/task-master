package bg.sofuni.mailsender.web.controller;

import bg.sofuni.mailsender.dto.Payload;
import bg.sofuni.mailsender.dto.SendMailDTO;
import bg.sofuni.mailsender.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<SendMailDTO> send(@RequestBody Payload mailDTO) throws MessagingException {
        mailService.send(mailDTO);
        return ResponseEntity.ok().build();
    }
}
