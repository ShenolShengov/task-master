package bg.softuni.taskmaster.init;


import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final Cloudinary cloudinary;

    @Override
    public void run(String... args) throws Exception {
        String generate = cloudinary.url().secure(true).generate("users/profile-pictures/default-profile-picture");
        System.out.println(generate);
    }
}
