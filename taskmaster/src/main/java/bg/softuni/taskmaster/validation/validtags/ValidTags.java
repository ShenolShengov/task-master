package bg.softuni.taskmaster.validation.validtags;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidTagsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidTags {

    String message() default "{validation.question.invalid.tags}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
