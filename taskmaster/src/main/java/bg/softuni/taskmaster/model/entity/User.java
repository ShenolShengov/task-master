package bg.softuni.taskmaster.model.entity;

import bg.softuni.taskmaster.anottation.SortParam;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    @SortParam
    private String username;

    @Column(name = "full_name", nullable = false)
    @SortParam
    private String fullName;

    @Column(nullable = false, unique = true)
    @SortParam
    private String email;

    @Column(nullable = false)
    @SortParam
    private Integer age;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Task> tasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Question> questions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Answer> answers;

    @OneToOne
    private Picture picture;

    public User() {
        this.roles = new LinkedHashSet<>();
        this.tasks = new LinkedHashSet<>();
        this.questions = new LinkedHashSet<>();
        this.answers = new LinkedHashSet<>();
    }
}
