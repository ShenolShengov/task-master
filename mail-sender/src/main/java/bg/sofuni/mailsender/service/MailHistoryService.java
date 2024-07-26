package bg.sofuni.mailsender.service;

import bg.sofuni.mailsender.enity.MailHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface MailHistoryService {

    Page<MailHistory> history(Instant filterByDate, Pageable pageable);

    void deleteOldHistory();
}
