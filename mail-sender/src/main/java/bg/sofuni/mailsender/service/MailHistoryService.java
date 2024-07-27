package bg.sofuni.mailsender.service;

import bg.sofuni.mailsender.dto.MailHistoryInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MailHistoryService {

    Page<MailHistoryInfoDTO> history(String filterByDate, Pageable pageable);

    void deleteOldHistory();

    void deleteHistory();

    Boolean hasHistory();
}
