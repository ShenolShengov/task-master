package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserProfileDTO;
import bg.softuni.taskmaster.service.UserModificationService;
import bg.softuni.taskmaster.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static bg.softuni.taskmaster.utils.MessageUtils.SUCCESSFULLY_CHANGE_PASSWORD_MESSAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserModificationController {

    private final UserModificationService modificationService;

    @GetMapping("/profile")
    public String profileView(Model model) {
        if (!model.containsAttribute("profileData")) {
            model.addAttribute("profileData", modificationService.getLoggedUserProfileDTO());
        }
        return "user-profile";
    }

    @PutMapping("/edit")
    public String edit(@Valid UserProfileDTO userProfileDTO, BindingResult bindingResult,
                       RedirectAttributes rAtt) throws IOException {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("profileData", userProfileDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.profileData",
                    bindingResult);
            return "redirect:/users/profile";
        }
        modificationService.edit(userProfileDTO);
        return "redirect:/";
    }

    @GetMapping("/change-password")
    public String changePasswordView(Model model) {
        if (!model.containsAttribute("changePasswordData")) {
            model.addAttribute("changePasswordData", new UserChangePasswordDTO());
        }
        return "change-password";
    }

    @PatchMapping("/change-password")
    public String changePassword(@Valid UserChangePasswordDTO changePasswordDTO, BindingResult bindingResult,
                                 RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("changePasswordData", changePasswordDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordData",
                    bindingResult);
            return "redirect:/users/change-password";
        }
        modificationService.changePassword(changePasswordDTO);
        rAtt.addFlashAttribute("messageToDisplay", SUCCESSFULLY_CHANGE_PASSWORD_MESSAGE);
        return "redirect:/";
    }
}
