package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String FOLDER = "users/profile-pictures";

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file, String pictureName) throws IOException {
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", FOLDER);
        options.put("public_id", pictureName);
        String id = (String) cloudinary.uploader().upload(file.getBytes(), options).get("public_id");
        return cloudinary.url().secure(true).generate(id);
    }

    private String formatUrl(String id) {
        return cloudinary.url().secure(true).transformation(new Transformation()
                .aspectRatio("1.0").width(250).crop("fill").chain()
                .radius("max").chain()
                .fetchFormat("auto")).generate(id);
    }
}
