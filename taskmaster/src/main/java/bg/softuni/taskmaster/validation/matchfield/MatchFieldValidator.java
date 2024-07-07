package bg.softuni.taskmaster.validation.matchfield;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class MatchFieldValidator implements ConstraintValidator<MatchField, Object> {

    private String first;
    private String second;

    @Override
    public void initialize(MatchField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

        return Objects.equals(beanWrapper.getPropertyValue(first), beanWrapper.getPropertyValue(second));
    }
}
