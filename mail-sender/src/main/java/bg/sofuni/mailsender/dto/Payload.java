package bg.sofuni.mailsender.dto;

import bg.sofuni.mailsender.dto.enums.EmailParam;
import bg.sofuni.mailsender.dto.enums.EmailTemplate;
import bg.sofuni.mailsender.validation.validemails.ValidEmails;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.EnumMap;

@Getter
@Setter
public class Payload implements Serializable {


    @NotEmpty
    @Email
    private String from;

    @NotEmpty
    @ValidEmails
    private String[] to;

    @NotNull
    private String subject;

    @NotNull
    private EmailTemplate template;

    @NotNull
    private EnumMap<EmailParam, String> params;

    public Payload() {
        this.params = new EnumMap<>(EmailParam.class);
    }
}
