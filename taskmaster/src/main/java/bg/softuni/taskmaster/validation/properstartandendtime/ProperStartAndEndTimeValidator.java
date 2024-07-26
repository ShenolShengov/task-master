package bg.softuni.taskmaster.validation.properstartandendtime;

import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProperStartAndEndTimeValidator implements ConstraintValidator<ProperStartAndEndTime, TaskAddEditDTO> {

    private String message;


    @Override
    public void initialize(ProperStartAndEndTime constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(TaskAddEditDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getStartTime() == null || value.getEndTime() == null) {
            return true;
        }
        boolean isValid = value.getStartTime().isBefore(value.getEndTime());
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("startTime")
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.task.end.time.after.start.time}")
                    .addPropertyNode("endTime")
                    .addConstraintViolation();
            context.disableDefaultConstraintViolation();
        }
        return isValid;
    }
}
