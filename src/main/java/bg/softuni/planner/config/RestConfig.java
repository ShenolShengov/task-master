package bg.softuni.planner.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestConfig {

    private final MailConfig mailConfig;

    @Bean("simple-rest-client")
    public RestClient simpleRestClient() {
        return RestClient.create();
    }

    @Bean("mail-rest-client")
    public RestClient mailRestClient() {
        return RestClient.builder()
                .baseUrl(mailConfig.getBaseUrl())
                .build();
    }
}
