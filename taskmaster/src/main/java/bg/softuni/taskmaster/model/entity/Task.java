package bg.softuni.taskmaster.model.entity;

import bg.softuni.taskmaster.model.enums.TaskPriorities;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriorities priority;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private boolean allDay;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    private User user;

    public Task() {

    }
}
