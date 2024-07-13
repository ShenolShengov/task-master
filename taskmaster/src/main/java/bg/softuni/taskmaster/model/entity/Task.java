package bg.softuni.taskmaster.model.entity;

import bg.softuni.taskmaster.anottation.SortParam;
import bg.softuni.taskmaster.model.enums.TaskPriorities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task extends BaseEntity {


    @Column(nullable = false)
    @SortParam
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @SortParam
    private TaskPriorities priority;

    @Column(nullable = false)
    private LocalDate dueDate;


    @Column(nullable = false)
    @SortParam
    private LocalTime startTime;

    @Column(nullable = false)
    @SortParam
    private LocalTime endTime;

    private boolean allDay;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    private User user;

    public Task() {

    }
}
