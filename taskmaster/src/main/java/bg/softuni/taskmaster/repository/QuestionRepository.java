package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

//    Set<Question> findAllByUserId(Long id);
}
