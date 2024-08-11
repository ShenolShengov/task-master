package bg.softuni.taskmaster.testutils;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.repository.PictureRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class PictureTestDataUtils {

    private static final String TEST_PICTURE_PATHNAME = "D:\\Temp\\TaskMaster\\taskmaster\\src\\test\\resources\\" +
                                                        "static\\images\\testPicture.png";
    private final PictureRepository pictureRepository;

    public PictureTestDataUtils(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }


    public Picture getDefaultProfilePicture() {
        return pictureRepository.readById(1L);
    }

    public static Picture getTestPicture() {
        return new Picture("defaultProfilePicture", "defaultUrl", "publicId");
    }


    public static MultipartFile getMultipartPicture() throws IOException {
        FileInputStream profilePictureStream =
                new FileInputStream(TEST_PICTURE_PATHNAME);
        return new MockMultipartFile("testPicture", profilePictureStream);
    }

    public static MultipartFile getEmptyMultipartFile() {
        return new MockMultipartFile("emptyPicture", new byte[]{});
    }

}
