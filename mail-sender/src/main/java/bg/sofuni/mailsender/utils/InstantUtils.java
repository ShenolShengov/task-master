package bg.sofuni.mailsender.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class InstantUtils {

    public static Instant toInstant(String filterByDate) {
        Instant now = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        return switch (filterByDate) {
            case "yesterday" -> now.minus(1, ChronoUnit.DAYS);
            case "7-days" -> now.minus(7, ChronoUnit.DAYS);
            case "30-days" -> now.minus(30, ChronoUnit.DAYS);
            default -> now;
        };
    }
}
