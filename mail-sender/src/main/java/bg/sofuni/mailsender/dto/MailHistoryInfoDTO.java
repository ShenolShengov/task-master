package bg.sofuni.mailsender.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class MailHistoryInfoDTO implements Serializable {

    private Long id;
    private String template;
    private Instant date;
    private String from;
    private String to;
}
