package bg.softuni.taskmaster.validation.uniquefield;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    private final UserRepository userRepository;
    private final UserHelperService userHelperService;
    private UniqueFieldType uniqueFieldType;
    private boolean checkForCurrentUser;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        uniqueFieldType = constraintAnnotation.value();
        this.checkForCurrentUser = constraintAnnotation.checkForLoggedUser();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<User> founded = switch (uniqueFieldType) {
            case USERNAME -> userRepository.findByUsername(value);
            case EMAIL -> userRepository.findByEmail(value);
        };
        return founded.filter(e -> !checkForCurrentUser || !e.getId().equals(userHelperService.getUser().getId()))
                .isEmpty();
    }
}
