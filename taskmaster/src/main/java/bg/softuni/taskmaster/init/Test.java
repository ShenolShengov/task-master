package bg.softuni.taskmaster.init;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final ModelMapper modelMapper;

    @Override
    public void run(String... args) throws Exception {
        Person person = new Person();
        PersonDTO map = modelMapper.map(person, PersonDTO.class);
        System.out.println(map.getTime());
    }
}

