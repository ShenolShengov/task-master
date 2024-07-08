package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
    @GetMapping("/{username}")
    public ResponseEntity<UserInfoDTO> userInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getInfo(username));
    }
}
