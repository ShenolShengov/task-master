package bg.softuni.taskmaster.validation.validstartandendtime;

import bg.softuni.taskmaster.validation.uniquefield.UniqueFieldType;
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

    String message() default "{field.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
