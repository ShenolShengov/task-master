package bg.softuni.planner.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ContactUsDTO {

    @NotNull
    @Length(min = 5, max = 200)
    private String title;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    @Length(min = 12, max = 2000)
    private String message;
}
