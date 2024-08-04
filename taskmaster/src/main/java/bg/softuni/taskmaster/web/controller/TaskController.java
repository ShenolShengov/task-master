package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.exceptions.TaskNotFoundException;
import bg.softuni.taskmaster.model.dto.TaskAddEditDTO;
import bg.softuni.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/add")
    public String addView(Model model) {
        if (!model.containsAttribute("addTaskData")) {
            model.addAttribute("addTaskData", new TaskAddEditDTO());
        }
        return "add-task";
    }

    @PostMapping("/add")
    public String add(@Valid TaskAddEditDTO taskAddEditDTO, BindingResult bindingResult,
                          RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addTaskData", taskAddEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addTaskData",
                    bindingResult);
            return "redirect:/tasks/add";
        }
        taskService.add(taskAddEditDTO);
        return redirectToHome();
    }

    @GetMapping("edit/{id}")
    public String editView(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("haveData")) {
            model.addAttribute("taskData", taskService.getInfo(id));
        }
        return "edit-task";
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("@taskServiceImpl.isActualUser(#taskEditDTO.id)")
    public String edit(@Valid TaskAddEditDTO taskEditDTO, BindingResult bindingResult,
                             RedirectAttributes rAtt, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("taskData", taskEditDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.taskData",
                    bindingResult);
            rAtt.addFlashAttribute("haveData", true);
            return "redirect:/tasks/edit/" + id;
        }
        taskService.edit(taskEditDTO);
        return redirectToHome();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return redirectToHome();
    }

    private static String redirectToHome() {
        return "redirect:/";
    }

}
