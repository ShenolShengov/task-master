package bg.sofuni.mailsender.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;

@Getter
@Setter
public class Payload {

    private String from;
    private String to;
    private String subject;
    private EmailTemplate template;
    private EnumMap<EmailParam, String> params;

    public Payload() {
        this.params = new EnumMap<>(EmailParam.class);
    }
}
