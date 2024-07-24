package bg.softuni.taskmaster.web;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PropertyReferenceException.class)
    public String returnToHome() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
    }


}
