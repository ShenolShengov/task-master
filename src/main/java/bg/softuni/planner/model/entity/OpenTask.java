package bg.softuni.planner.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "open_tasks")
public class OpenTask extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;

    @ManyToOne(optional = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "open_tasks_upvotes",
            joinColumns = @JoinColumn(name = "open_taks_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> upVotes;

}
