package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.matchfield.MatchField;
import bg.softuni.taskmaster.validation.uniquefield.NotTaken;
import bg.softuni.taskmaster.validation.uniquefield.NotTakenType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@MatchField(
        first = "password",
        second = "confirmPassword",
        message = "{validation.user.passwords.not.match}"
)
@Getter
@Setter
public class UserRegisterDTO {

    @NotNull(message = "{validation.user.username.length}")
    @Length(min = 2, max = 15, message = "{validation.user.username.length}")
    @NotTaken(value = NotTakenType.USERNAME, message ="{validation.user.username.unique}")
    private String username;

    @NotNull(message = "{validation.user.full.name.length}")
    @Length(min = 2, max = 30, message = "{validation.user.full.name.length}")
    private String fullName;

    @Email(message = "{validation.user.email.not.valid}")
    @NotEmpty(message = "{validation.user.email.not.valid}")
    @NotTaken(value = NotTakenType.EMAIL, message ="{validation.email.username.unique}")
    private String email;

    @Positive(message = "{validation.user.age.positive}")
    @NotNull(message = "{validation.user.age.positive}")
    private Integer age;

    @NotNull(message = "{validation.user.password.length}")
    @Length(min = 5, max = 15, message = "{validation.user.password.length}")
    private String password;

    @NotNull(message = "{validation.user.password.length}")
    @Length(min = 5, max = 15, message = "{validation.user.password.length}")
    private String confirmPassword;
}
