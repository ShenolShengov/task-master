package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.service.PagingAndSortingService;
import bg.softuni.taskmaster.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.Set;

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
                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                          @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        pagingAndSortingService.setUp(page - 1, sortBy, sortDirection);
        model.addAttribute("foundedUsers",
                userService.getAllInfo(pagingAndSortingService.getPageable()));

        return "all-users";
    }

    @GetMapping("/search/username")
    public String search(@RequestParam(value = "username") String username, Model model) {
        Set<UserInfoDTO> foundedUsers = userService.searchByUsername(username);
        model.addAttribute("foundedUsers", foundedUsers);
        pagingAndSortingService.filterResult((long) foundedUsers.size());
        return "all-users";
    }


//    @GetMapping("/next-page")
//    public String nextPage(String redirectUrl) {
//        pagingAndSortingService.nextPage();
//        return "redirect:" + redirectUrl;
//    }
//
//    @GetMapping("/prev-page")
//    public String prevPage() {
//        pagingAndSortingService.prevPage();
//        return "redirect:/users";
//    }

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
