package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import bg.sofuni.mailsender.service.MailHistoryService;
import bg.sofuni.mailsender.utils.InstantUtils;
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
    public Page<MailHistory> history(String filterByDate, Pageable pageable) {
        if (filterByDate.equals("all")) {
            return mailHistoryRepository.findAll(pageable);
        }
        Instant date = InstantUtils.toInstant(filterByDate);
        if (filterByDate.equals("today") || filterByDate.equals("yesterday")) {
            return mailHistoryRepository.findAllFor(date, pageable);
        }
        return mailHistoryRepository.findAllByDateAfter(date, pageable);
    }

    @Override
    public void deleteOldHistory() {
        Instant deleteBefore = Instant.now().minus(retentionPeriod);
        mailHistoryRepository.deleteOldHistory(deleteBefore);
    }

    @Override
    public void deleteHistory() {
        mailHistoryRepository.deleteAll();
    }

    @Override
    public Boolean hasHistory() {
        return mailHistoryRepository.count() > 0;
    }
}
