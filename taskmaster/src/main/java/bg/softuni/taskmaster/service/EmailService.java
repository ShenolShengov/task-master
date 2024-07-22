package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailParam;
import bg.softuni.taskmaster.model.enums.EmailTemplate;

import java.util.EnumMap;

public interface EmailService {

    void send(Payload contactUsDTO);

    Payload createPayload(String from, String to, String subject, EmailTemplate template,
                          EnumMap<EmailParam, String> params);
}
