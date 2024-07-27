package bg.sofuni.mailsender.enity;

import bg.sofuni.mailsender.dto.enums.EmailTemplate;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "mail_history")
@Getter
@Setter
@NoArgsConstructor
public class MailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "`from`")
    private String from;

    @Column(nullable = false, name = "`to`")
    private String to;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailTemplate template;

    public MailHistory(String from, String to, Instant date, EmailTemplate template) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.template = template;
    }
}
