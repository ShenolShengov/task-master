package bg.softuni.taskmaster.validation.uniquefield;

import bg.softuni.taskmaster.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotTakenValidator implements ConstraintValidator<NotTaken, String> {

    private final UserRepository userRepository;
    private NotTakenType notTakenType;
    private long currentUser;

    @Override
    public void initialize(NotTaken constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        notTakenType = constraintAnnotation.value();
        this.currentUser = constraintAnnotation.currentUser();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (notTakenType) {
            case USERNAME -> userRepository.findByUsername(value).filter(e -> e.getId() != currentUser).isEmpty();
            case EMAIL -> userRepository.findByEmail(value).filter(e -> e.getId() != currentUser).isEmpty();
        };
    }
}
