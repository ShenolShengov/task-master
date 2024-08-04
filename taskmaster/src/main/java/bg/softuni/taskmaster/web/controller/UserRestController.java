package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.exceptions.UserNotFoundException;
import bg.softuni.taskmaster.service.AuthorizationService;
import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/api")
public class UserRestController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/make-admin/{id}")
    public ResponseEntity<Void> makeAdmin(@PathVariable Long id) {
        authorizationService.makeAdmin(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/remove-admin/{id}")
    public ResponseEntity<Void> removeAdmin(@PathVariable Long id) {
        authorizationService.removeAdmin(id);
        return ResponseEntity.ok().build();
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> handleUserNotFoundException() {
        return ResponseEntity.notFound().build();
    }

}
