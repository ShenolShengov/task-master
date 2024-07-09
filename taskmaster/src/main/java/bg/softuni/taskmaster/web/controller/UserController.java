package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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


    @PostMapping("/login-error")
    public String loginError(@ModelAttribute("username") String username, Model model) {
        model.addAttribute("hasError", true);
        model.addAttribute("username", username);
        return "login";
    }


    @GetMapping
    public String allView(Model model) {
        model.addAttribute("allUsers", userService.getAllInfo());
        if (!model.containsAttribute("editData")) {
            model.addAttribute("editData", new UserEditDTO());
        }
        if (!model.containsAttribute("changePasswordData")) {
            model.addAttribute("changePasswordData", new UserChangePasswordDTO());
        }
        return "all-users";
    }

    @PutMapping
    public String edit(@Valid UserEditDTO userEditDTO, BindingResult bindingResult,
                       RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("editData", userEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.editData",
                    bindingResult);
            rAtt.addFlashAttribute("showEdit", true);
            return "redirect:/users";
        }
        userService.edit(userEditDTO);
        return "redirect:/users";
    }

    @PatchMapping("/change-password")
    public String changePassword(@Valid UserChangePasswordDTO changePasswordDTO, BindingResult bindingResult,
                                 RedirectAttributes rAtt){
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("changePasswordData", changePasswordDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordData",
                    bindingResult);
            rAtt.addFlashAttribute("showChangePassword", true);
            return "redirect:/users";
        }
        userService.changePassword(changePasswordDTO);
        return "redirect:/users";
    }
}
