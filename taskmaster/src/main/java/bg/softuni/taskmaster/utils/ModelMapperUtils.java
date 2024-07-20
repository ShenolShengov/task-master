package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.UserAnswerDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterEditDTO;
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
import java.util.List;

public class ModelMapperUtils {

    public static ModelMapper getModifiedModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().getConverters().add(localDateTimeToStringConverter());
        modelMapper.getConfiguration().getConverters().add(profilePictureOrDefaultProfilePictureConverter());
        modelMapper.typeMap(UserRegisterEditDTO.class, User.class)
                .addMappings(m -> m.using(ModelMapperUtils.encodePasswordConverter())
                        .map(UserRegisterEditDTO::getPassword, User::setPassword));

        modelMapper.typeMap(Question.class, QuestionBaseInfoDTO.class)
                .addMappings(m -> m.using(collectionsToSizeConverter())
                        .map(Question::getAnswers, QuestionBaseInfoDTO::setAnswers));
        return modelMapper;
    }


    private static ConditionalConverter<User, UserAnswerDTO> profilePictureOrDefaultProfilePictureConverter() {
        return new ConditionalConverter<>() {
            @Override
            public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
                return User.class.isAssignableFrom(sourceType) && UserAnswerDTO.class.isAssignableFrom(destinationType)
                        ? MatchResult.FULL : MatchResult.NONE;
            }

            @Override
            public UserAnswerDTO convert(MappingContext<User, UserAnswerDTO> context) {
                User user = context.getSource();
                return UserAnswerDTO.builder()
                        .username(user.getUsername())
                        .profilePictureUrl(user.getProfilePicture() == null ? "/images/default-profile-picture.png"
                                : user.getProfilePicture().getUrl())
                        .build();
            }
        };
    }

    private static Converter<List<Answer>, Integer> collectionsToSizeConverter() {
        return context -> context.getSource().size();
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
