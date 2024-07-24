package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.events.AccountDeletionEvent;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.service.AuthorizationService;
import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/api")
public class UserRestController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> userInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getInfo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userService.exists(id)) {
            return ResponseEntity.badRequest().build();
        }
        userService.delete(id);
        UserInfoDTO info = userService.getInfo(id);
        publisher.publishEvent(new AccountDeletionEvent(this, info.getUsername(), info.getEmail()));
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


}
