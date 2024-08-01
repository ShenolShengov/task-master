package bg.softuni.taskmaster.repository;

import bg.softuni.taskmaster.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture readById(Long id);
}
