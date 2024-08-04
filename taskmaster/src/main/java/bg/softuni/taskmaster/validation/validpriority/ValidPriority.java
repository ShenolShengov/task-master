package bg.softuni.taskmaster.validation.validpriority;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidPriorityValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidPriority {

    String message() default "{validation.task.priority.not.valid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
