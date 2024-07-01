package bg.softuni.planner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("users/register")
    public String registerView() {
        return "register";
    }

    @GetMapping("users/login")
    public String loginView() {
        return "login";
    }
}
