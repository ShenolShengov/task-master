package bg.softuni.taskmaster.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false)
    private Instant createdTime;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Question question;

    public Answer() {
        this.createdTime = Instant.now();
    }
}
