package bg.softuni.taskmaster.web;

import bg.softuni.taskmaster.exceptions.ObjectNotFoundException;
import bg.softuni.taskmaster.exceptions.TaskNotFoundException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
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

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleQuestionNotFoundException(ObjectNotFoundException ex, Model model) {
        model.addAttribute("type", ex.getType());
        return "objectNotFound";
    }
}
