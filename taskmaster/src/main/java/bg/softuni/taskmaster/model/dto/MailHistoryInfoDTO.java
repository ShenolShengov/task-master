package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MailHistoryInfoDTO implements Serializable {

    private String template;
    @DateTimeFormat(pattern = "dd-MM-yyyy  kk:mm")
    private LocalDateTime date;
    private String from;
    private String to;
}
