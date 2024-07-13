package bg.softuni.taskmaster.validation.validstartandendtime;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidStartAndEndTimeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidStartAndEndTime {

    String message() default "{validation.task.invalid.start.and.end.time}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
