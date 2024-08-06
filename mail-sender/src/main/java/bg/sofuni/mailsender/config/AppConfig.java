package bg.sofuni.mailsender.config;

import bg.sofuni.mailsender.dto.Payload;
import bg.sofuni.mailsender.enity.MailHistory;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Payload.class, MailHistory.class)
                .addMappings(m -> m.using(emailsArrayToString())
                        .map(Payload::getTo, MailHistory::setTo));
        return modelMapper;
    }

    private Converter<String[], String> emailsArrayToString() {
        return context -> String.join(", ", context.getSource());
    }
}
