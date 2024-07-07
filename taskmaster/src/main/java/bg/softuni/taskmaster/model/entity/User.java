package bg.softuni.taskmaster.model.entity;

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
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<PersonalTask> personalTasks;

    public User() {
        this.roles = new LinkedHashSet<>();
        this.personalTasks = new LinkedHashSet<>();
    }
}
