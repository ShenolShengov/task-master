package bg.softuni.taskmaster.validation.notmatchloggeduserpassword;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MatchLoggedUserPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotMatchLoggedUserPassword {

    String message() default "{validation.change.to.same.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
