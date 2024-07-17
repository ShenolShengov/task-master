package bg.sofuni.mailsender.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateAssertionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TemplateAssertionException.class)
    public String error() {

        return "";
    }
}
