package bg.softuni.planner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/register")
    public String registerView() {
        return "register";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/all")
    public String allView(){
        return "all-users";
    }
}
