package bg.softuni.taskmaster.validation.validstartandendtime;

import bg.softuni.taskmaster.model.dto.TaskAddDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidStartAndEndTimeValidator implements ConstraintValidator<ValidStartAndEndTime, TaskAddDTO> {

    @Override
    public boolean isValid(TaskAddDTO value, ConstraintValidatorContext context) {
        if (value.getStartTime() == null || value.getEndTime() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.task.time.not.null}")
                    .addConstraintViolation();
            return false;
        }
        return value.getStartTime().isBefore(value.getEndTime());
    }
}
