package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import bg.sofuni.mailsender.service.MailHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class MailHistoryServiceImpl implements MailHistoryService {

    private final MailHistoryRepository mailHistoryRepository;

    @Value("${history.retention.period}")
    private Period retentionPeriod;


    @Override
    public Page<MailHistory> history(Pageable pageable) {
        return mailHistoryRepository.findAll(pageable);
    }

    @Override
    public void deleteOldHistory() {
        Instant deleteBefore = Instant.now().minus(retentionPeriod);
        mailHistoryRepository.deleteOldHistory(deleteBefore);
    }
}
