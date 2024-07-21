package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerRestController {

    private final AnswerService answerService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Answer> delete(@PathVariable Long id) {
        if (!answerService.isActualUserOrAdmin(id)) {
            return ResponseEntity.badRequest().build();
        }
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
