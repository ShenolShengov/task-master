package bg.softuni.taskmaster.validation.notmatchloggeduserpassword;

import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MatchLoggedUserPasswordValidator implements ConstraintValidator<NotMatchLoggedUserPassword, String> {

    private final UserHelperService userHelperService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !passwordEncoder.matches(value, userHelperService.getUser().getPassword());
    }
}
