package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.repository.PictureRepository;
import bg.softuni.taskmaster.service.CloudinaryService;
import bg.softuni.taskmaster.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    @Override
    public Picture createPicture(MultipartFile pictureFile, String pictureName) throws IOException {
        if (pictureFile == null) {
            throw new RuntimeException();
        }
        String pictureUrl = cloudinaryService.uploadPicture(pictureFile, pictureName);
        Picture picture = new Picture(pictureFile.getOriginalFilename(), pictureUrl);
        pictureRepository.save(picture);
        return picture;
    }

    @Override
    public Picture getDefultProfilePicture() {
        return pictureRepository.findByOriginalName("default-profile-picture");
    }
}
