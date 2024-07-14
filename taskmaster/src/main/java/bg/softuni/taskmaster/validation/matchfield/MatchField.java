package bg.softuni.taskmaster.validation.matchfield;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = MatchFieldValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface MatchField {

    String message() default "{validation.field.unique}";

    String first();

    String second();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
