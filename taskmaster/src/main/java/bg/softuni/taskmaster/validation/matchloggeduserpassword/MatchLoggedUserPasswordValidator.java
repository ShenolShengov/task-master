package bg.softuni.taskmaster.validation.matchloggeduserpassword;

import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MatchLoggedUserPasswordValidator implements ConstraintValidator<MatchLoggedUserPassword, String> {

    private final UserHelperService userHelperService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return passwordEncoder.matches(value, userHelperService.getUser().getPassword());
    }
}
