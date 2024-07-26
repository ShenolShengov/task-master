package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.MailHistoryInfoDTO;
import bg.softuni.taskmaster.model.dto.Payload;
import bg.softuni.taskmaster.model.enums.EmailParam;
import bg.softuni.taskmaster.model.enums.EmailTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.EnumMap;

public interface MailService {

    void send(Payload contactUsDTO);

    Payload createPayload(String from, String to, String subject, EmailTemplate template,
                          EnumMap<EmailParam, String> params);

    Page<MailHistoryInfoDTO> history(String filterByDate, Pageable pageable);

    void deleteHistory();

    boolean hasHistory();
}
