package bg.softuni.taskmaster.model.anottation;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SortParam {

    String message() default "";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
