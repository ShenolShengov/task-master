package bg.softuni.taskmaster.model.entity;

import bg.softuni.taskmaster.model.enums.TaskPriorities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "personal_tasks")
@Getter
@Setter
public class PersonalTask extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriorities priority;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    private User user;

}
