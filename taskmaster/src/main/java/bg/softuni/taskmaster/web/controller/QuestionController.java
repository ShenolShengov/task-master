package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.exceptions.QuestionNotFoundException;
import bg.softuni.taskmaster.model.dto.AnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.service.QuestionService;
import bg.softuni.taskmaster.service.AnswerService;
import bg.softuni.taskmaster.utils.SortingUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final AnswerService answerService;

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) Integer ignoredPage,
                         @RequestParam(required = false, defaultValue = "createdTime,desc")
                         String sort,
                         @RequestParam(name = "search_query", required = false, defaultValue = "") String searchQuery,
                         @PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC)
                         Pageable pageable) {
        SortingUtils.addSelectedSortOptions(model, sort, "desc");
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
    public String ask(@Valid QuestionAskDTO questionAskDTO, BindingResult bindingResult,
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
    @PreAuthorize("@questionServiceImpl.isExists(#id)")
    public String answer(@PathVariable Long id, @Valid AnswerDTO answerDTO,
                         BindingResult bindingResult, RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("questionAnswerData", answerDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.questionAnswerData",
                    bindingResult);
            rAtt.addFlashAttribute("invalidData", true);
        } else {
            answerService.answer(answerDTO, id);
            rAtt.addFlashAttribute("successfullyAddedComment", true);
        }
        return "redirect:/questions/" + id;
    }

    @GetMapping("/{id}")
    public String detailsView(@PathVariable Long id, Model model) {
        model.addAttribute("questionData", questionService.getDetailsInfoDTO(id));
        if (!model.containsAttribute("questionAnswerData")) {
            model.addAttribute("questionAnswerData", new AnswerDTO());
        }
        return "question-details";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        questionService.delete(id);
        return "redirect:/";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleQuestionNotFoundException() {
        return "questionNotFound";
    }
}
