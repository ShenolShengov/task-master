package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.MailHistoryInfoDTO;
import bg.softuni.taskmaster.service.MailService;
import bg.softuni.taskmaster.utils.SortingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MaiHistoryController {

    private final MailService mailService;

    @GetMapping("/mail-history")
    public String emailHistoryView(Model model,
                                   @RequestParam(required = false) Integer ignoredPage,
                                   @RequestParam(required = false, defaultValue = "date,desc")
                                   String sort,
                                   @RequestParam(required = false, defaultValue = "today") String filterByDate,
                                   @PageableDefault(sort = "date", direction = Sort.Direction.DESC)
                                   Pageable pageable) {

        Page<MailHistoryInfoDTO> history = mailService.history(filterByDate, pageable.previousOrFirst());
        model.addAttribute("history", history);
        SortingUtils.addSelectedSortOptions(model, sort, "desc");
        model.addAttribute("filterByDate", filterByDate);
        model.addAttribute("hasHistory", mailService.hasHistory());
        return "mail-history";
    }

    @DeleteMapping("/mail-history")
    public String deleteMailHistory() {
        mailService.deleteHistory();
        return "redirect:/mail-history";
    }
}
