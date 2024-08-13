package bg.sofuni.mailsender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetentionScheduler {

    private final MailHistoryService mailHistoryService;

    @Scheduled(cron = "0 0 0 * * *")//cron = */10 * * * * * - cron for every 10 seconds
    public void deleteOldHistory() {
        mailHistoryService.deleteOldHistory();
    }
}
