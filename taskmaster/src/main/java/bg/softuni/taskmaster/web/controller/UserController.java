package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.taskmaster.utils.SortingUtils.addSelectedSortOptions;
import static bg.softuni.taskmaster.utils.SortingUtils.checkForDefaultSorting;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @ModelAttribute("editData")
    public UserEditDTO editData() {
        return userService.getCurrentUserEditData();
    }

    @ModelAttribute("changePasswordData")
    public UserChangePasswordDTO changePasswordData() {
        return new UserChangePasswordDTO();
    }


    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) Integer ignoredPage,
                         @RequestParam(required = false, defaultValue = ",asc")
                         String sort,
                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                         Pageable pageable,
                         @RequestParam(name = "search_query", required = false, defaultValue = "") String searchQuery

    ) {
        pageable = checkForDefaultSorting(sort, pageable);
        addSelectedSortOptions(model, sort);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("foundedUsers",
                userService.getAll(searchQuery, pageable.previousOrFirst()));
        return "all-users";
    }


    @GetMapping("/edit")
    public String editUser() {
        return "edit-user";
    }

    @PutMapping("/edit")
    public String editView(@Valid UserEditDTO userEditDTO, BindingResult bindingResult,
                           RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("editData", userEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.editData",
                    bindingResult);
            return "redirect:/users/edit";
        }
        userService.edit(userEditDTO);
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
        userService.changePassword(changePasswordDTO);
        return "redirect:/";
    }
}
