package bg.softuni.taskmaster.validation.uniquefield;

import bg.softuni.taskmaster.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    private final UserRepository userRepository;
    private UniqueFieldType uniqueFieldType;
    private long currentUser;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        uniqueFieldType = constraintAnnotation.value();
        this.currentUser = constraintAnnotation.currentUser();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (uniqueFieldType) {
            case USERNAME -> userRepository.findByUsername(value).filter(e -> e.getId() != currentUser).isEmpty();
            case EMAIL -> userRepository.findByEmail(value).filter(e -> e.getId() != currentUser).isEmpty();
        };
    }
}
