package bg.softuni.taskmaster.init;

import bg.softuni.taskmaster.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final CloudinaryService cloudinaryService;

    @Override
    public void run(String... args) throws Exception {
//        File file = new File("D:\\Temp\\TaskMaster\\taskmaster\\src\\main\\resources\\static\\images\\how-it-works.png");
//        System.out.println(cloudinaryService.uploadImage(file));
//        System.out.println(file);
    }
}
