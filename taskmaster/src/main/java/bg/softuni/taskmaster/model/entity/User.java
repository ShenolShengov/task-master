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

    @ManyToOne
    private Picture profilePicture;

    public User() {
        this.roles = new LinkedHashSet<>();
        this.tasks = new LinkedHashSet<>();
        this.questions = new LinkedHashSet<>();
        this.answers = new LinkedHashSet<>();
    }

    public User(String username, String fullName, String email, Integer age, String password,
                Set<Role> roles, Set<Task> tasks, Set<Question> questions, Set<Answer> answers, Picture profilePicture) {
        super();
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.password = password;
        this.roles = roles;
        this.tasks = tasks;
        this.questions = questions;
        this.answers = answers;
        this.profilePicture = profilePicture;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
