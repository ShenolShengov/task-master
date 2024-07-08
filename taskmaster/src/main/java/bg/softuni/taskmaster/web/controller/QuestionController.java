package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.AskQuestionDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.service.QuestionService;
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
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping()
    public String questionsView() {
        return "questions";
    }

    @GetMapping("/ask")
    public String askView(Model model) {
        if (!model.containsAttribute("askQuestionData")) {
            model.addAttribute("askQuestionData", new AskQuestionDTO());
        }
        return "ask-question";
    }

    @PostMapping("/ask")
    public String doRegister(@Valid AskQuestionDTO askQuestionDTO, BindingResult bindingResult,
                             RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("askQuestionData", askQuestionDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.askQuestionData",
                    bindingResult);
            return "redirect:/questions/ask";
        }
        long questionId = questionService.ask(askQuestionDTO);
        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/answer/{id}")
    public String answer(@PathVariable Long id, @Valid QuestionAnswerDTO questionAnswerDTO,
                         BindingResult bindingResult, RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("questionAnswerData", questionAnswerDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.questionAnswerData",
                    bindingResult);
            rAtt.addFlashAttribute("scrollToFrom", true);
        } else {
            questionService.answer(questionAnswerDTO, id);
        }
        return "redirect:/questions/" + id;
    }

    @GetMapping("/{id}")
    public String detailsView(@PathVariable Long id, Model model) {
        model.addAttribute("questionData", questionService.getDetailsDTO(id));
        if (!model.containsAttribute("questionAnswerData")) {
            model.addAttribute("questionAnswerData", new QuestionAnswerDTO());
        }
        return "questions-details";
    }
}
