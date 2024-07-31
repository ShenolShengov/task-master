package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByUserUsername(String userUsername, Pageable pageable);


    @Query("SELECT q FROM Question q JOIN q.user u WHERE  " +
           "CAST(q.createdTime AS date) = :createdTime AND u.username = :userUsername")
    Page<Question> findAllByUserUsernameAndCreatedTimeDate(String userUsername, LocalDate createdTime, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE " +
           "q.title like CONCAT('%', :searchQuery, '%') OR " +
           "q.tags like CONCAT('%', :searchQuery, '%')")
    Page<Question> search(String searchQuery, Pageable pageable);
}
