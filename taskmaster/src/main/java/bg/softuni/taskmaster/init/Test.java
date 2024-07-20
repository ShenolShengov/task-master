package bg.softuni.taskmaster.init;


import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final Cloudinary cloudinary;

    @Override
    public void run(String... args) throws Exception {
        Integer biggestNumber = Stream.of(1, 2, 7, 4, 6, 5).max(Integer::compareTo).get();
        System.out.println(biggestNumber);
    }
}
