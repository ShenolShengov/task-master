package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    Picture createPicture(MultipartFile pictureFile, String folder, String pictureName) throws IOException;

    Picture getDefultProfilePicture();

    void deletePicture(Picture profilePicture) throws IOException;
}
