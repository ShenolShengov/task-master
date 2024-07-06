package bg.softuni.planner.validation.uniquefield;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueFieldValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueField {

    String message() default "{field.unique}";

     UniqueFieldType value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
