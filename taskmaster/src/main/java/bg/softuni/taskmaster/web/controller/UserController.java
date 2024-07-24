package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static bg.softuni.taskmaster.utils.SortingUtils.addSelectedSortOptions;
import static bg.softuni.taskmaster.utils.SortingUtils.checkForDefaultSorting;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserHelperService userHelperService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getAll(Model model,
                         @RequestParam(required = false) Integer ignoredPage,
                         @RequestParam(required = false, defaultValue = ",asc")
                         String sort,
                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                         @RequestParam(name = "search_query", required = false, defaultValue = "") String searchQuery,
                         Pageable pageable

    ) {
        pageable = checkForDefaultSorting(sort, pageable);
        addSelectedSortOptions(model, sort);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("foundedUsers",
                userService.getAll(searchQuery, pageable.previousOrFirst()));
        return "all-users";
    }

    @DeleteMapping("/close-account")
    public String closeAccount() {
        userService.delete(userHelperService.getLoggedUser().getId());
        SecurityContextHolder.getContext().setAuthentication(null);//todo ask for this
        return "redirect:/";
    }
}
