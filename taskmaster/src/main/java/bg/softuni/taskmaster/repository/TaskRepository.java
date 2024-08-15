package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByUserUsernameAndDueDate(String userUserName, LocalDate dueDate, Pageable pageable);

    @Query("DELETE FROM Task t WHERE t.dueDate < :deleteBefore")
    void deleteOldTasks(LocalDate deleteBefore);
}
