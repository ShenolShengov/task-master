package bg.softuni.planner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/home")
    public String homeView(){
        return "home";
    }
}
