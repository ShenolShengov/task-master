package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.matchloggeduserpassword.MatchLoggedUserPassword;
import bg.softuni.taskmaster.validation.matchfield.MatchField;
import bg.softuni.taskmaster.validation.notmatchloggeduserpassword.NotMatchLoggedUserPassword;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@MatchField(
        first = "newPassword",
        second = "confirmPassword",
        message = "{validation.user.passwords.not.match}"
)
public class UserChangePasswordDTO {

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
