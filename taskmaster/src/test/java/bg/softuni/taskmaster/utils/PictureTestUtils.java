package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.repository.PictureRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PictureTestUtils {

    public static PictureRepository pictureRepository;


    public static Picture getDefaultProfilePicture(boolean fromDB) {
        Picture profilePicture = new Picture("defaultProfilePicture", "defaultUrl", "publicId");
        if (!fromDB) return profilePicture;
        Optional<Picture> defaultPicture = pictureRepository.findById(1L);
        return defaultPicture.orElseGet(() -> pictureRepository
                .save(profilePicture));
    }


    public static void setPictureRepository(PictureRepository pictureRepository) {
        PictureTestUtils.pictureRepository = pictureRepository;
    }
}
