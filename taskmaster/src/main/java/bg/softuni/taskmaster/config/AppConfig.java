package bg.softuni.taskmaster.config;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' kk:mm");

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Question.class, QuestionDetailsInfoDTO.class)
                .addMappings(m -> m.using(fromLocalDateTimeToString())
                        .map(Question::getCreatedTime, QuestionDetailsInfoDTO::setCreatedTime));
        modelMapper.typeMap(Answer.class, AnswerDetailsDTO.class)
                .addMappings(m -> m.using(fromLocalDateTimeToString())
                        .map(Answer::getCreatedTime, AnswerDetailsDTO::setCreatedTime));
        return modelMapper;
    }

    private static Converter<LocalDateTime, String> fromLocalDateTimeToString() {
        return mappingContext -> DATE_TIME_FORMATTER.format(mappingContext.getSource());
    }

}
