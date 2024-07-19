package bg.softuni.taskmaster.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadPicture(MultipartFile file, String pictureName) throws IOException;
}
