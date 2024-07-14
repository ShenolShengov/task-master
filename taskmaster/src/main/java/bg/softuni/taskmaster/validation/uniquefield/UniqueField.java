package bg.softuni.taskmaster.validation.uniquefield;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UniqueFieldValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueField {

    String message() default "{validation.field.unique}";

    UniqueFieldType value();
    long currentUser() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
