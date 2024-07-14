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

@Getter
@Setter
@MatchField(first = "id", second = "fullName")
public class UserEditDTO {

    @NotNull
    private Long id;

    @NotNull(message = "{validation.user.username.length}")
    @Length(min = 2, max = 15, message = "{validation.user.username.length}")
    @UniqueField(value = UniqueFieldType.USERNAME, checkForLoggedUser = true, message = "{validation.user.username.unique}")
    private String username;

    @NotNull(message = "{validation.user.full.name.length}")
    @Length(min = 2, max = 30, message = "{validation.user.full.name.length}")
    private String fullName;

    @Email(message = "{validation.user.email.not.valid}")
    @NotEmpty(message = "{validation.user.email.not.valid}")
    @UniqueField(value = UniqueFieldType.EMAIL, checkForLoggedUser = true, message = "{validation.email.unique}")
    private String email;

    @Positive(message = "{validation.user.age.positive}")
    @NotNull(message = "{validation.user.age.positive}")
    private Integer age;
}
