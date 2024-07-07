package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.TaskAddDTO;
import bg.softuni.taskmaster.model.dto.UserRegisterDTO;
import bg.softuni.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    @GetMapping("/add-task")
    public String addTaskView(Model model) {
        if (!model.containsAttribute("addTaskData")) {
            model.addAttribute("addTaskData", new TaskAddDTO());
        }
        return "add-task";
    }

    @PostMapping("/add-task")
    public String doAddTask(@Valid TaskAddDTO taskAddDTO, BindingResult bindingResult,
                            RedirectAttributes rAtt){
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addTaskData", taskAddDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addTaskData",
                    bindingResult);
            return "redirect:/tasks/add-task";
        }
        taskService.add(taskAddDTO);
        return "redirect:/";
    }

    @GetMapping("edit-task")
    public String editTask() {
        return "edit-task";
    }
}
