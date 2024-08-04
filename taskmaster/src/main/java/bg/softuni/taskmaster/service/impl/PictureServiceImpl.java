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

    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Picture savePicture(MultipartFile pictureFile, String folder) throws IOException {
        if (pictureFile.isEmpty()) {
            return getDefaultPicture();
        }
        String publicId = cloudinaryService.uploadPicture(pictureFile, folder);
        String pictureUrl = cloudinaryService.getUrl(publicId);
        Picture picture = new Picture(pictureFile.getOriginalFilename(), pictureUrl, publicId);
        return pictureRepository.save(picture);
    }

    private Picture getDefaultPicture() {
        return pictureRepository.readById(1L);
    }

    @Override
    public void deletePicture(Picture profilePicture) throws IOException {
        cloudinaryService.deletePicture(profilePicture.getPublicId());
        pictureRepository.delete(profilePicture);
    }
}
