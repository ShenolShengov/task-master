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
    public String uploadPicture(MultipartFile file, String folder) throws IOException {
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", folder);
        return (String) cloudinary.uploader().upload(file.getBytes(), options).get("public_id");
    }
    @Override
    public void deletePicture(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    @Override
    public String getUrl(String publicId) {
        return cloudinary.url().secure(true).generate(publicId);
    }
}
