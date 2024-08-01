package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.repository.PictureRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Component
public class PictureTestUtils {

    public static final String TEST_PICTURE_PATHNAME = "D:\\Temp\\TaskMaster\\taskmaster\\src\\test\\resources\\" +
                                                       "static\\images\\testPicture.png";
    public static PictureRepository pictureRepository;


    public static Picture getPicture(boolean fromDB) {
        Picture profilePicture = new Picture("defaultProfilePicture", "defaultUrl", "publicId");
        if (!fromDB) return profilePicture;
        Optional<Picture> defaultPicture = pictureRepository.findById(1L);
        return defaultPicture.orElseGet(() -> pictureRepository
                .save(profilePicture));
    }


    public static MultipartFile getMultipartPicture() throws IOException {
        FileInputStream profilePictureStream =
                new FileInputStream(TEST_PICTURE_PATHNAME);
        return new MockMultipartFile("testPicture", profilePictureStream);
    }

    public static MultipartFile getEmptyMultipartFile() {
        return new MockMultipartFile("emptyPicture", new byte[]{});
    }

    public static void setPictureRepository(PictureRepository pictureRepository) {
        PictureTestUtils.pictureRepository = pictureRepository;
    }
}
