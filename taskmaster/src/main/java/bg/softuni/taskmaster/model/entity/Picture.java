package bg.softuni.taskmaster.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pictures")
@Getter
@Setter
@NoArgsConstructor
public class Picture extends BaseEntity {

    private String originalName;
    private String url;

    public Picture(String originalName, String url) {
        this.originalName = originalName;
        this.url = url;
    }
}
