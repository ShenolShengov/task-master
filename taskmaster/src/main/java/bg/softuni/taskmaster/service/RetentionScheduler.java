package bg.softuni.taskmaster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetentionScheduler {

    private final TaskService taskService;


    @Scheduled(cron = "0 0 0 * * *")//cron = */10 * * * * * - cron for every 10 seconds
    private void deleteOldTasks() {
        taskService.deleteOldTasks();
    }
}
