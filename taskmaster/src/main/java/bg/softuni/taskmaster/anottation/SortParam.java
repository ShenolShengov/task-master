package bg.softuni.taskmaster.anottation;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SortParam {

    String message() default "";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
