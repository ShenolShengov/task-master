package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.service.PagingAndSortingService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final PagingAndSortingService<User> pagingAndSortingService;

    @ModelAttribute("editData")
    public UserEditDTO editData() {
        return new UserEditDTO();
    }

    @ModelAttribute("changePasswordData")
    public UserChangePasswordDTO changePasswordData() {
        return new UserChangePasswordDTO();
    }

    @GetMapping
    public String allView(Model model,
                          @RequestParam(required = false, defaultValue = "1") Integer page,
                          @RequestParam(required = false, defaultValue = "id") String sortBy,
                          @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) {
        pagingAndSortingService.setUp(page - 1, sortBy, sortDirection);
        model.addAttribute("foundedUsers",
                userService.getAllInfo(pagingAndSortingService.getPageable()));

        return "all-users";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "id") String sortBy,
                         @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                         @RequestParam(value = "search_query") String searchQuery

    ) {
        pagingAndSortingService.setUp(page - 1, sortBy, sortDirection, searchQuery);
        Map.Entry<Integer, List<UserInfoDTO>> foundedWithTotalElements =
                userService.search(searchQuery, pagingAndSortingService.getPageable());
        pagingAndSortingService.setTotalElements(foundedWithTotalElements.getKey());
        model.addAttribute("foundedUsers", foundedWithTotalElements.getValue());
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
                                 RedirectAttributes rAtt) {
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
