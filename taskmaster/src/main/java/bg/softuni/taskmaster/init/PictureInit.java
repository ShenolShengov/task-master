package bg.softuni.taskmaster.init;

import com.cloudinary.Cloudinary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PictureInit implements CommandLineRunner {

    private Cloudinary cloudinary;

    @Override
    public void run(String... args) throws Exception {

    }
}
