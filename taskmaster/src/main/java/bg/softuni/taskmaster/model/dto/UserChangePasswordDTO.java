package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.matchloggeduserpassword.MatchLoggedUserPassword;
import bg.softuni.taskmaster.validation.matchfield.MatchPasswords;
import bg.softuni.taskmaster.validation.notmatchloggeduserpassword.NotMatchLoggedUserPassword;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Setter
@MatchPasswords(
        password = "newPassword",
        confirmPassword = "confirmPassword",
        message = "{validation.user.passwords.not.match}"
)
public class UserChangePasswordDTO implements Serializable {

    @MatchLoggedUserPassword
    private String currentPassword;

    @NotNull(message = "{validation.user.password.length}")
    @Length(min = 5, max = 15, message = "{validation.user.password.length}")
    @NotMatchLoggedUserPassword
    private String newPassword;

    @NotNull(message = "{validation.user.password.length}")
    @Length(min = 5, max = 15, message = "{validation.user.password.length}")
    @NotMatchLoggedUserPassword
    private String confirmPassword;
}
