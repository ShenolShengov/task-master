package bg.sofuni.mailsender.service;

import bg.sofuni.mailsender.enity.MailHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MailHistoryService {

    Page<MailHistory> history(Pageable pageable);
}
