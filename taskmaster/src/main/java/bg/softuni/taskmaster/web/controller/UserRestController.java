package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/api")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> userInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getInfo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/make-admin/{id}")
    public ResponseEntity<Void> makeAdmin(@PathVariable Long id) {
        userService.makeAdmin(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/remove-admin/{id}")
    public ResponseEntity<Void> removeAdmin(@PathVariable Long id) {
        userService.removeAdmin(id);
        return ResponseEntity.ok().build();
    }


}
