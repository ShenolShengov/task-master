package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.matchfield.MatchField;
import bg.softuni.taskmaster.validation.uniquefield.UniqueField;
import bg.softuni.taskmaster.validation.uniquefield.UniqueFieldType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@MatchField(
        first = "password",
        second = "confirmPassword"
)
@Getter
@Setter
public class UserRegisterDTO {

    @NotNull(message = "Username length must be between 5 and 15 symbols!")
    @Length(min = 2, max = 15, message = "Username length must be between 5 and 15 symbols!")
    @UniqueField(UniqueFieldType.USERNAME)
    private String username;

    @NotNull(message = "Full name length must be between 2 and 20 symbols!")
    @Length(min = 2, max = 20, message = "Full name length must be between 2 and 20 symbols!")
    private String fullName;

    @Email(message = "Not valid email")
    @NotEmpty(message = "Not valid email")
    @UniqueField(UniqueFieldType.EMAIL)
    private String email;

    @Positive(message = "Age must be positive")
    @NotNull(message = "Age must be positive")
    private Integer age;

    @NotNull(message = "Password length must be between 2 and 15 symbols!")
    @Length(min = 2, max = 15, message = "Password length must be between 2 and 15 symbols!")
    private String password;

    @NotNull(message = "Password length must be between 5 and 15 symbols!")
    @Length(min = 2, max = 15, message = "Password length must be between 2 and 15 symbols!")
    private String confirmPassword;
}
