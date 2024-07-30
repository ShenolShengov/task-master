package bg.softuni.taskmaster.model.dto;

import bg.softuni.taskmaster.model.enums.EmailParam;
import bg.softuni.taskmaster.model.enums.EmailTemplate;
import lombok.*;

import java.io.Serializable;
import java.util.EnumMap;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Payload implements Serializable {

    private String from;
    private String to;
    private String subject;
    private EmailTemplate template;
    private EnumMap<EmailParam, String> params;

    public Payload() {
        this.params = new EnumMap<>(EmailParam.class);
    }
}
