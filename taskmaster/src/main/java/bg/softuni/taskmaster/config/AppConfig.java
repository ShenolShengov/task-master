package bg.softuni.taskmaster.config;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.PagingAndSortingService;
import bg.softuni.taskmaster.service.impl.PagingAndSortingServiceImpl;
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
        modelMapper.typeMap(Question.class, QuestionDetailsDTO.class)
                .addMappings(m -> m.using(fromLocalDateTimeToString())
                        .map(Question::getCreatedTime, QuestionDetailsDTO::setCreatedTime));
        modelMapper.typeMap(Answer.class, AnswerDetailsDTO.class)
                .addMappings(m -> m.using(fromLocalDateTimeToString())
                        .map(Answer::getCreatedTime, AnswerDetailsDTO::setCreatedTime));
        return modelMapper;
    }

    private static Converter<LocalDateTime, String> fromLocalDateTimeToString() {
        return mappingContext -> DATE_TIME_FORMATTER.format(mappingContext.getSource());
    }

    @Bean(name = "usersPagingAndSorting")
    public PagingAndSortingService<User> userPagingAndSortingService(UserRepository userRepository) {
        return new PagingAndSortingServiceImpl<>(userRepository, User.class);
    }
}
