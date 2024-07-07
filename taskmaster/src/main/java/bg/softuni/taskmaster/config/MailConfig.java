package bg.softuni.taskmaster.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail.api")
@Getter
@Setter
public class MailConfig {

    private String baseUrl;
}
