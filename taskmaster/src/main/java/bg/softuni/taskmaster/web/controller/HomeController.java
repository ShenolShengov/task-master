package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    @GetMapping("/")
    public String indexView(Model model) {
        if (isAnonymous()){
            return "index";
        }
        model.addAttribute("currentUserTasks", userService.getTasks());
        return "home";
    }


    @GetMapping("/about")
    public String aboutView() {
        return "about";
    }

    private static boolean isAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }
}
