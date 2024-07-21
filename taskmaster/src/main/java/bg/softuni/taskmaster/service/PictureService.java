package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    Picture createPictureOrGetDefault(MultipartFile pictureFile, String folder) throws IOException;


    void deletePicture(Picture profilePicture) throws IOException;
}
