package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import bg.softuni.taskmaster.service.TaskService;
import bg.softuni.taskmaster.service.UserHelperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserHelperService userHelperService;

    @GetMapping("/add-task")
    public String addTaskView(Model model) {
        if (!model.containsAttribute("addTaskData")) {
            model.addAttribute("addTaskData", new TaskAddEditDTO());
        }
        return "add-task";
    }

    @PostMapping("/add-task")
    public String doAddTask(@Valid TaskAddEditDTO taskAddEditDTO, BindingResult bindingResult,
                            RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addTaskData", taskAddEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addTaskData",
                    bindingResult);
            return "redirect:/tasks/add-task";
        }
        taskService.add(taskAddEditDTO);
        return "redirect:/";
    }

    @GetMapping("edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        if (!userHelperService.getLoggedUser().getId().equals(id)) {
            return "redirect:/";
        }
        if (!model.containsAttribute("haveData")) {
            model.addAttribute("taskData", taskService.getInfo(id));
        }
        return "edit-task";
    }

    @PostMapping("/edit/{id}")
    public String doEditTask(@Valid TaskAddEditDTO taskEditDTO, BindingResult bindingResult,
                             RedirectAttributes rAtt, @PathVariable Long id) {
        if (!userHelperService.getLoggedUser().getId().equals(id)) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("taskData", taskEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.taskData",
                    bindingResult);
            rAtt.addFlashAttribute("haveData", true);
            return "redirect:/tasks/edit/" + id;
        }
        taskService.edit(taskEditDTO);
        return "redirect:/";
    }
}
