package bg.softuni.taskmaster.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadPicture(MultipartFile file, String folder) throws IOException;

    void deletePicture(String publicId) throws IOException;

    String getUrl(String publicId);
}
