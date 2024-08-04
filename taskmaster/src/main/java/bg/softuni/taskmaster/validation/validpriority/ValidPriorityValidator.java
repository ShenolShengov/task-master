package bg.softuni.taskmaster.validation.validpriority;

import bg.softuni.taskmaster.model.enums.TaskPriorities;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class ValidPriorityValidator implements ConstraintValidator<ValidPriority, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Arrays.stream(TaskPriorities.values()).anyMatch(e -> e.name().equals(value));
    }
}
