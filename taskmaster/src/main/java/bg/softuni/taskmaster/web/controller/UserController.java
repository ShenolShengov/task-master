package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/register")
    public String registerView(Model model) {

        if (!model.containsAttribute("registerData")) {
            model.addAttribute("registerData", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult,
                             RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("registerData", userRegisterDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.registerData",
                    bindingResult);
            return "redirect:/users/register";
        }
        userService.register(userRegisterDTO);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model, RedirectAttributes rAtt) {
        rAtt.addFlashAttribute("hasError", true);
        return "redirect:/users/login";
    }

    @GetMapping("/all")
    public String allView() {
        return "all-users";
    }
}
