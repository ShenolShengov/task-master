package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.service.QuestionService;
import bg.softuni.taskmaster.service.TaskService;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.utils.SortingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static bg.softuni.taskmaster.utils.SortingUtils.checkForDefaultSorting;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TaskService taskService;
    private final QuestionService questionService;
    private final UserHelperService userHelperService;

    @GetMapping("/")
    @PreAuthorize("hasRole('Admin')")
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
    public String statisticsView() {
        return "statistics";
    }

    @GetMapping("/email-history")
    public String emailHistoryView(Model model) {
        model.addAttribute("founded",
                new PageImpl<>(List.of(), PageRequest.of(0, 5), 0));
        return "mail-history";
    }

}
