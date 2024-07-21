package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.service.UserModificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserModificationController {

    private final UserModificationService modificationService;

    @ModelAttribute("editData")
    public UserEditDTO editData() {
        return modificationService.getCurrentUserEditData();
    }

    @ModelAttribute("changePasswordData")
    public UserChangePasswordDTO changePasswordData() {
        return new UserChangePasswordDTO();
    }

    @GetMapping("/edit")
    public String editUser() {
        return "edit-user";
    }

    @PutMapping("/edit")
    public String editView(@Valid UserEditDTO userEditDTO, BindingResult bindingResult,
                           RedirectAttributes rAtt) throws IOException {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("editData", userEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.editData",
                    bindingResult);
            return "redirect:/users/edit";
        }
        modificationService.edit(userEditDTO);
        return "redirect:/";
    }

    @GetMapping("/change-password")
    public String changePasswordView() {
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
        return "redirect:/";
    }
}
