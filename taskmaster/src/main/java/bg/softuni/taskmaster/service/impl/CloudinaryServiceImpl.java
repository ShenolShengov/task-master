package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {


    private final Cloudinary cloudinary;

    @Override
    public String uploadPicture(MultipartFile file, String folder, String pictureName) throws IOException {
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", folder);
        options.put("public_id", pictureName);
        String id = (String) cloudinary.uploader().upload(file.getBytes(), options).get("public_id");
        return cloudinary.url().secure(true).generate(id);
    }

    @Override
    public void deletePicture(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
