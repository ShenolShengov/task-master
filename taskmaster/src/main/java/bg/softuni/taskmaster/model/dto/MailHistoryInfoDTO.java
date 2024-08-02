package bg.softuni.taskmaster.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailHistoryInfoDTO implements Serializable {

    private Long id;
    private String template;
    private Instant date;
    private String from;
    private String to;
}
