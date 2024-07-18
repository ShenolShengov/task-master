package bg.softuni.taskmaster.init;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Person {
    LocalDateTime time;

    public Person() {
        this.time = LocalDateTime.now();
    }
}
