package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.matchfield.MatchPasswords;
import bg.softuni.taskmaster.validation.unique.email.UniqueEmail;
import bg.softuni.taskmaster.validation.unique.username.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@MatchPasswords(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "{validation.user.passwords.not.match}"
)
@Getter
@Setter
public class UserRegisterDTO implements Serializable {

    @NotNull(message = "{validation.user.username.length}")
    @Length(min = 2, max = 15, message = "{validation.user.username.length}")
    @UniqueUsername(message = "{validation.user.username.unique}")
    private String username;

    @NotNull(message = "{validation.user.full.name.length}")
    @Length(min = 2, max = 30, message = "{validation.user.full.name.length}")
    private String fullName;

    @Email(message = "{validation.user.email.not.valid}")
    @NotEmpty(message = "{validation.user.email.not.valid}")
    @UniqueEmail(message = "{validation.user.email.unique}")
    private String email;

    @NotNull(message = "{validation.user.age.positive}")
    @Positive(message = "{validation.user.age.positive}")
    private Integer age;

    @NotNull(message = "{validation.user.password.length}")
    @Length(min = 5, max = 15, message = "{validation.user.password.length}")
    private String password;

    @NotNull(message = "{validation.user.password.length}")
    @Length(min = 5, max = 15, message = "{validation.user.password.length}")
    private String confirmPassword;

    private MultipartFile profilePicture;
}
