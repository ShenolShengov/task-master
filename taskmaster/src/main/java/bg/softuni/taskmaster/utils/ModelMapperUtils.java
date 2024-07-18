package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.dto.AnswerDetailsDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelMapperUtils {

    public static ModelMapper getModifiedModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().getConverters().add(ModelMapperUtils.localDateTimeToStringConverter());
        modelMapper.typeMap(UserRegisterDTO.class, User.class)
                .addMappings(m -> m.using(ModelMapperUtils.encodePasswordConverter())
                        .map(UserRegisterDTO::getPassword, User::setPassword));
        return modelMapper;
    }

    private static ConditionalConverter<LocalDateTime, String> localDateTimeToStringConverter() {
        return new ConditionalConverter<>() {
            @Override
            public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
                return LocalDateTime.class.isAssignableFrom(sourceType) && String.class.isAssignableFrom(destinationType) ? MatchResult.FULL : MatchResult.NONE;
            }

            @Override
            public String convert(MappingContext<LocalDateTime, String> context) {
                return context.getSource().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' kk:mm"));
            }
        };
    }

    private static Converter<String, String> encodePasswordConverter() {
        return context -> Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(context.getSource());
    }
}
