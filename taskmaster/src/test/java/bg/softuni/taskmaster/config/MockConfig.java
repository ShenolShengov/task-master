package bg.softuni.taskmaster.config;

import bg.softuni.taskmaster.service.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class MockConfig {

    @Primary
    @Bean
    public MailService mailService() {
        return mock(MailService.class);
    }
}
