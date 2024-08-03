package bg.softuni.taskmaster.web;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.FOUND)
    public String returnToHome() {
        return "redirect:/" + ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString().split("/+")[2];
    }
}
