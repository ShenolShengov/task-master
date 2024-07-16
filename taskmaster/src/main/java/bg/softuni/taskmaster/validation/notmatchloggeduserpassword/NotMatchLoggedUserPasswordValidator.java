package bg.softuni.taskmaster.validation.notmatchloggeduserpassword;

import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class NotMatchLoggedUserPasswordValidator implements ConstraintValidator<NotMatchLoggedUserPassword, String> {

    private final UserHelperService userHelperService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return true;
        }
        return !passwordEncoder.matches(password, userHelperService.getUser().getPassword());
    }
}
