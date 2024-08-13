package bg.softuni.taskmaster.validation.validtags;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ValidTagsValidator implements ConstraintValidator<ValidTags, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank() || value.split("\\s+").length == 0) {
            context.
                    buildConstraintViolationWithTemplate("{validation.question.tags.not.empty}")
                    .addConstraintViolation().
                    disableDefaultConstraintViolation();
            return false;
        }
        List<String> tags = Arrays.stream(value.split("\\s+")).toList();
        return tags.size() <= 5 && !tags.isEmpty() && tags.stream().distinct().count() == tags.size();
    }
}
