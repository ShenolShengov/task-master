package bg.softuni.planner.validation.uniquefield;

import bg.softuni.planner.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    private final UserRepository userRepository;
    private UniqueFieldType uniqueFieldType;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        uniqueFieldType = constraintAnnotation.value();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (uniqueFieldType) {
            case USERNAME -> userRepository.findByUsername(value).isEmpty();
            case EMAIL -> userRepository.findByEmail(value).isEmpty();
        };
    }
}
