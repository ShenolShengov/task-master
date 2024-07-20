package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserRegisterEditDTO;
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

import java.io.IOException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerView(Model model) {
        if (!model.containsAttribute("registerData")) {
            model.addAttribute("registerData", new UserRegisterEditDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterEditDTO userRegisterEditDTO, BindingResult bindingResult,
                             RedirectAttributes rAtt) throws IOException {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("registerData", userRegisterEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.registerData",
                    bindingResult);
            return "redirect:/users/register";
        }
        userService.register(userRegisterEditDTO);
        return "redirect:/users/login";
    }
}
