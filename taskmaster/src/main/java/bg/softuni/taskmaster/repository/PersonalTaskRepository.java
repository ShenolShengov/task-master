package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.PersonalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTaskRepository extends JpaRepository<PersonalTask, Long> {
}
