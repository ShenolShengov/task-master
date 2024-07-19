package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) Integer ignoredPage,
                         @RequestParam(required = false, defaultValue = "createdTime,asc")
                         String sort,
                         @RequestParam(name = "search_query", required = false, defaultValue = "") String searchQuery,
                         @PageableDefault(size = 1, sort = "createdTime", direction = Sort.Direction.ASC)
                         Pageable pageable) {
        model.addAttribute("sortDirection", sort.split(",")[1]);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("foundedQuestions",
                questionService.getAll(searchQuery, pageable.previousOrFirst()));
        return "questions";
    }

    @GetMapping("/ask")
    public String askView(Model model) {
        if (!model.containsAttribute("askQuestionData")) {
            model.addAttribute("askQuestionData", new QuestionAskDTO());
        }
        return "ask-question";
    }

    @PostMapping("/ask")
    public String doRegister(@Valid QuestionAskDTO questionAskDTO, BindingResult bindingResult,
                             RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("askQuestionData", questionAskDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.askQuestionData",
                    bindingResult);
            return "redirect:/questions/ask";
        }
        long questionId = questionService.ask(questionAskDTO);
        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/answer/{id}")
    public String answer(@PathVariable Long id, @Valid QuestionAnswerDTO questionAnswerDTO,
                         BindingResult bindingResult, RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("questionAnswerData", questionAnswerDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.questionAnswerData",
                    bindingResult);
            rAtt.addFlashAttribute("invalidData", true);
        } else {
            questionService.answer(questionAnswerDTO, id);
            rAtt.addFlashAttribute("successfullyAddedComment", true);
        }
        return "redirect:/questions/" + id;
    }

    @GetMapping("/{id}")
    public String detailsView(@PathVariable Long id, Model model) {
        model.addAttribute("questionData", questionService.getDetailsInfoDTO(id));
        if (!model.containsAttribute("questionAnswerData")) {
            model.addAttribute("questionAnswerData", new QuestionAnswerDTO());
        }
        return "questions-details";
    }
}
