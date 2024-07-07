package bg.softuni.taskmaster.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private OpenTask task;

}
