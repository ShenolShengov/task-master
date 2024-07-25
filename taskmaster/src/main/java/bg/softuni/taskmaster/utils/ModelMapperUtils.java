package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModelMapperUtils {

    public static ModelMapper getModifiedModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().getConverters().add(localDateTimeToStringConverter());
        modelMapper.typeMap(UserRegisterDTO.class, User.class)
                .addMappings(m -> m.using(ModelMapperUtils.encodePasswordConverter())
                        .map(UserRegisterDTO::getPassword, User::setPassword));

        modelMapper.typeMap(Question.class, QuestionBaseInfoDTO.class)
                .addMappings(m -> m.using(collectionsToSizeConverter())
                        .map(Question::getAnswers, QuestionBaseInfoDTO::setAnswers));
        return modelMapper;
    }

    private static Converter<List<Answer>, Integer> collectionsToSizeConverter() {
        return context -> context.getSource().size();
    }

    private static ConditionalConverter<Instant, String> localDateTimeToStringConverter() {
        return new ConditionalConverter<>() {
            @Override
            public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
                return Instant.class.isAssignableFrom(sourceType) && String.class.isAssignableFrom(destinationType) ? MatchResult.FULL : MatchResult.NONE;
            }

            @Override
            public String convert(MappingContext<Instant, String> context) {
                return DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' kk:mm").withZone(ZoneId.systemDefault())
                        .format(context.getSource());
            }
        };
    }

    private static Converter<String, String> encodePasswordConverter() {
        return context -> Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(context.getSource());
    }
}
