package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static bg.sofuni.mailsender.dto.enums.EmailTemplate.CONTACT_US;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailHistoryServiceIT {

    @Autowired
    private MailHistoryRepository mailHistoryRepository;


    @Autowired
    private MailHistoryServiceImpl mailHistoryServiceToTest;

    @Test
    public void test_deleteOldHistory() {
        mailHistoryRepository.save(new MailHistory("testFrom@", "testTo@",
                Instant.now().minus(370, ChronoUnit.DAYS), CONTACT_US));
        mailHistoryServiceToTest.deleteOldHistory();
        assertEquals(0, mailHistoryRepository.count());
    }
}