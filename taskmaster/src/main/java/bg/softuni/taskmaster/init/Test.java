package bg.softuni.taskmaster.init;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.PictureRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    @Override
    public void run(String... args) throws Exception {
//        User user = userRepository.findByUsername("Shenol10").get();
//        System.out.println(user.getPicture());
    }
}
