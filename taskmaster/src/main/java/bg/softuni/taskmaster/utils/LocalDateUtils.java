package bg.softuni.taskmaster.utils;

import java.time.LocalDate;

public class LocalDateUtils {

    public static LocalDate orToday(LocalDate date) {
        if (date == null) {
            return LocalDate.now();
        }
        return date;
    }
}
