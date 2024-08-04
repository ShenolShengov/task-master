package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.exceptions.AnswerNotFoundException;
import bg.softuni.taskmaster.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers/api/")
@RequiredArgsConstructor
public class AnswerRestController {

    private final AnswerService answerService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<Void> handleAnswerNotFoundException() {
        return ResponseEntity.badRequest().build();
    }
}
