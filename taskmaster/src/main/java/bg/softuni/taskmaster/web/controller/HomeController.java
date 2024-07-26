package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.MailHistoryInfoDTO;
import bg.softuni.taskmaster.service.*;
import bg.softuni.taskmaster.utils.InstantUtils;
import bg.softuni.taskmaster.utils.SortingUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static bg.softuni.taskmaster.utils.SortingUtils.checkForDefaultSorting;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TaskService taskService;
    private final QuestionService questionService;
    private final UserHelperService userHelperService;
    private final StatisticsService statisticsService;
    private final EmailService emailService;

    @GetMapping("/")
    public String indexView(Model model,
                            @RequestParam(name = "task_due_date", required = false) LocalDate taskDueDate,
                            @RequestParam(name = "task_sort", defaultValue = ",asc") String taskSort,
                            @Qualifier("task")
                            @PageableDefault(size = Integer.MAX_VALUE, sort = "id", direction = Sort.Direction.ASC)
                            Pageable taskPageable,
                            @RequestParam(name = "question_created_time", required = false)
                            LocalDate questionCreatedTime,
                            @RequestParam(name = "question_sort", defaultValue = "createdTime,desc") String questionSort,
                            @Qualifier("question")
                            @PageableDefault(size = Integer.MAX_VALUE, sort = "createdTime", direction = Sort.Direction.DESC)
                            Pageable questionPageable
    ) {
        if (!userHelperService.isAuthenticated()) {
            return "index";
        }
        taskPageable = checkForDefaultSorting(taskSort, taskPageable);
        taskDueDate = taskDueDate == null ? LocalDate.now() : taskDueDate;
        SortingUtils.addSelectedSortOptions(model, taskSort, "task_");
        model.addAttribute("task_due_date", taskDueDate);
        model.addAttribute("userTasks", taskService.getTasksFor(taskDueDate, taskPageable));

        SortingUtils.addSelectedSortOptions(model, questionSort, "question_");
        model.addAttribute("question_created_time", questionCreatedTime);
        model.addAttribute("userQuestions", questionService
                .getQuestionsFrom(questionCreatedTime, questionPageable));

        return "home";
    }


    @GetMapping("/about")
    public String aboutView() {
        return "about";
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public String statisticsView(Model model) {
        model.addAttribute("statisticsData", statisticsService.getStatistics());
        return "statistics";
    }

    @GetMapping("/mail-history")
    @PreAuthorize("hasRole('ADMIN')")
    public String emailHistoryView(Model model,
                                   @RequestParam(required = false) Integer ignoredPage,
                                   @RequestParam(required = false, defaultValue = "date,desc")
                                   String sort,
                                   @RequestParam(required = false, defaultValue = "today") String filterByDate,
                                   @PageableDefault(sort = "date", direction = Sort.Direction.DESC)
                                   Pageable pageable) {

        Page<MailHistoryInfoDTO> history = emailService.history(InstantUtils.toInstant(filterByDate), pageable.previousOrFirst());
        model.addAttribute("history", history);
        model.addAttribute("sortDirection", sort.split(",")[1]);
        model.addAttribute("filterByDate", filterByDate);
        return "mail-history";
    }

}
