package bg.softuni.taskmaster.config;

import bg.softuni.taskmaster.utils.ModelMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return ModelMapperUtils.getModifiedModelMapper();
    }
}
