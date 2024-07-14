package bg.softuni.taskmaster.validation.matchloggeduserpassword;

import bg.softuni.taskmaster.validation.matchfield.MatchFieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MatchLoggedUserPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MatchLoggedUserPassword {

    String message() default "{validation.wrong.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
