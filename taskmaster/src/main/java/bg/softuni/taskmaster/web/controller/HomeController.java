package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String indexView() {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return "index";
        }
        return "home";
    }

    @GetMapping("/home")
    public String homeView() {
        return "home";
    }

    @GetMapping("/about")
    public String aboutView() {
        return "about";
    }

}
