package bg.softuni.taskmaster.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestConfig {


    @Value("${mail.api.baseUrl}")
    private String emailApiBaseUrl;

    @Bean("simple-rest-client")
    public RestClient simpleRestClient() {
        return RestClient.create();
    }

    @Bean("mail-rest-client")
    public RestClient mailRestClient() {
        return RestClient.builder()
                .baseUrl(emailApiBaseUrl)
                .build();
    }
}
