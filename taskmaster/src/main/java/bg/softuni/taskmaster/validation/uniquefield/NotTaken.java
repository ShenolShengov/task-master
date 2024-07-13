package bg.softuni.taskmaster.validation.uniquefield;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.Map;

@Constraint(validatedBy = NotTakenValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotTaken {

    String message() default "{validation.field.unique}";

    NotTakenType value();

    long currentUser() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
