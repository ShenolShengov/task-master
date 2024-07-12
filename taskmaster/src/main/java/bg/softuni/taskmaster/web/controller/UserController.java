package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.UserChangePasswordDTO;
import bg.softuni.taskmaster.model.dto.UserEditDTO;
import bg.softuni.taskmaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.web.PageableDefault;
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
                          @RequestParam(required = false) Integer ignoredPage,
                          @RequestParam(required = false, defaultValue = ",asc")
                          String sort,
                          @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC)
                          Pageable pageable
    ) {
        pageable = checkForDefaultSorting(sort, pageable);
        addSelectedSortOptionToModel(model, sort);
        model.addAttribute("foundedUsers",
                userService.getAllInfo(pageable.previousOrFirst()));

        return "all-users";
    }

    private static Pageable checkForDefaultSorting(String sort, Pageable pageable) {
        if (sort.startsWith(",")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.Direction.fromString(sort.substring(1)), "id");
        }
        return pageable;
    }

    private static void addSelectedSortOptionToModel(Model model, String sort) {
        String[] sortTokens = sort.split(",");
        model.addAttribute("sortProperties", sortTokens[0]);
        model.addAttribute("sortDirection", sortTokens[1]);
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(required = false) Integer ignoredPage,
                         @RequestParam(required = false, defaultValue = ",asc")
                         String sort,
                         @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC)
                         Pageable pageable,

                         @RequestParam(name = "search_query", required = false, defaultValue = "") String searchQuery

    ) {
        pageable = checkForDefaultSorting(sort, pageable);
        addSelectedSortOptionToModel(model, sort);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("foundedUsers",
                userService.search(searchQuery, pageable.previousOrFirst()));
        return "all-users";
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public String returnToHome() {
        return "redirect:/users";
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
