package bg.softuni.taskmaster.validation.matchfield;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class MatchPasswordsValidator implements ConstraintValidator<MatchPasswords, Object> {


    private String password;
    private String confirmPassword;
    private String message;

    @Override
    public void initialize(MatchPasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

        boolean isValid = Objects.equals(beanWrapper.getPropertyValue(password),
                beanWrapper.getPropertyValue(confirmPassword));

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmPassword)
                    .addConstraintViolation();
            context.disableDefaultConstraintViolation();
        }
        return isValid;
    }
}
