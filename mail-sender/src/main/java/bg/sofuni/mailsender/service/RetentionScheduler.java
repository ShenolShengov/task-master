package bg.sofuni.mailsender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetentionScheduler {

    private final MailHistoryService mailHistoryService;

    @Scheduled(cron = "*/10 * * * * *")//for example
    public void deleteOldHistory() {
        mailHistoryService.deleteOldHistory();
    }
}
