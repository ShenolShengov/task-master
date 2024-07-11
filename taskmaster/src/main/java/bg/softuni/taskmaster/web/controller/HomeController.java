package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final UserHelperService userHelperService;

    @GetMapping("/")
    public String indexView(Model model) {
        if (!userHelperService.isAuthenticated()) {
            return "index";
        }
        model.addAttribute("currentUserTasks", userService.getTasks());
        return "home";
    }


    @GetMapping("/about")
    public String aboutView() {
        return "about";
    }

    @GetMapping("/statistics")
    public String statisticsView() {
        return "statistics";
    }

}
