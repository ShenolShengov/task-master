package bg.sofuni.mailsender.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlUtils {

    public static String currentUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
    }
}
