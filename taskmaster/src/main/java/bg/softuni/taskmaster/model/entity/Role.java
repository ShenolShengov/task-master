package bg.softuni.taskmaster.model.entity;

import bg.softuni.taskmaster.model.enums.UserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoles name;

    public Role(UserRoles name) {
        this.name = name;
    }
}
