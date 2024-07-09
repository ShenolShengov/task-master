package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.validation.matchfield.MatchField;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@MatchField(first = "newPassword", second = "confirmPassword")
public class UserChangePasswordDTO {

    @NotNull
    private Long id;

    @NotNull(message = "Password length must be between 2 and 15 symbols!")
    @Length(min = 2, max = 15, message = "Password length must be between 2 and 15 symbols!")
    private String newPassword;

    @NotNull(message = "Password length must be between 2 and 15 symbols!")
    @Length(min = 2, max = 15, message = "Password length must be between 2 and 15 symbols!")
    private String confirmPassword;
}
