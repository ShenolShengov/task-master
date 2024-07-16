package bg.softuni.taskmaster.validation.matchfield;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = MatchPasswordsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MatchPasswords {

    String message() default "{validation.field.unique}";

    String password();

    String confirmPassword();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
