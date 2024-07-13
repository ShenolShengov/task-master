package bg.softuni.taskmaster.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ContactUsDTO {

    @NotNull(message = "{validation.title.length}")
    @Length(min = 5, max = 200, message = "{validation.title.length}")
    private String title;

    @Email(message = "{validation.user.email.not.valid}")
    @NotEmpty(message = "{validation.user.email.not.valid}")
    private String email;

    @NotNull(message = "{validation.contact.us.message.length}")
    @Length(min = 12, max = 2000, message = "{validation.contact.us.message.length}")
    private String message;
}
