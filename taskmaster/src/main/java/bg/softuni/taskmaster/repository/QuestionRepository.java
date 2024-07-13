package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByUserId(Long userId, Pageable pageable);


    @Query("SELECT q FROM Question q JOIN q.user u WHERE  CAST(q.createdTime AS date) = :createdTime AND u.id = :userId")
    Page<Question> findAllByUserIdAndCreatedTimeDate(Long userId, LocalDate createdTime, Pageable pageable);
}
