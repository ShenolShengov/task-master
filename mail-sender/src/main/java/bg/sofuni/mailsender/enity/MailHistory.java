package bg.sofuni.mailsender.enity;

import bg.sofuni.mailsender.dto.enums.EmailTemplate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "mail_history")
@Getter
@Setter
public class MailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailTemplate template;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false, name = "`from`")
    private String from;

    @Column(nullable = false, name = "`to`")
    private String to;

}
