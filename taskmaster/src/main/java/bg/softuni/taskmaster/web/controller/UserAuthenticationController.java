package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String registerView(Model model) {
        if (!model.containsAttribute("registerData")) {
            model.addAttribute("registerData", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult,
                           RedirectAttributes rAtt) throws IOException {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("registerData", userRegisterDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.registerData",
                    bindingResult);
            return "redirect:/users/register";
        }
        authenticationService.register(userRegisterDTO);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";

    }

    @PostMapping("/login-error")
    public String loginErrorView(@ModelAttribute("username") String username, Model model) {
        model.addAttribute("hasError", true);
        model.addAttribute("username", username);
        return "login";
    }
}
