package bg.softuni.taskmaster.validation.unique.username;

import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;
    private final UserHelperService userHelperService;
    private boolean checkForCurrentUser;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.checkForCurrentUser = constraintAnnotation.checkForLoggedUser();

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) {
            return true;
        }
        return userRepository.findByUsername(username)
                .filter(e -> !checkForCurrentUser || !e.getId().equals(userHelperService.getLoggedUser().getId()))
                .isEmpty();
    }
}
