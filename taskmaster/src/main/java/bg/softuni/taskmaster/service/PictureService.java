package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    Picture uploadPicture(MultipartFile pictureFile, String pictureName) throws IOException;
}
