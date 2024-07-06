package bg.softuni.planner.repository;

import bg.softuni.planner.model.entity.Comment;
import bg.softuni.planner.model.entity.OpenTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenTaskRepository extends JpaRepository<OpenTask, Long> {
}
