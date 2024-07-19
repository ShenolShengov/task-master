package bg.softuni.taskmaster.validation.unique.email;

import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;
    private final UserHelperService userHelperService;
    private boolean checkForCurrentUser;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.checkForCurrentUser = constraintAnnotation.checkForLoggedUser();

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        return userRepository.findByEmail(email)
                .filter(e -> !checkForCurrentUser || !e.getId().equals(userHelperService.getLoggedUser().getId()))
                .isEmpty();
    }
}
