package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.OpenTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenTaskRepository extends JpaRepository<OpenTask, Long> {
}
