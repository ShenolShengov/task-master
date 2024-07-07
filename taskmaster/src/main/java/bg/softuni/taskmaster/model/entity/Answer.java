package bg.softuni.taskmaster.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Question question;

}
