package bg.softuni.taskmaster.model.entity;

import bg.softuni.taskmaster.anottation.SortParam;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question extends BaseEntity {

    @Column(nullable = false)
    @SortParam
    private String title;

    @Column(nullable = false)
    private String tags;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false)
    @SortParam
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @ManyToOne(optional = false)
    private User user;


    public Question() {
        this.answers = new TreeSet<>(Comparator.comparing(Answer::getCreatedTime));
    }
}
