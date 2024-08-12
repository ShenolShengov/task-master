package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.taskmaster.utils.EmailUtils.SUCCESSFULLY_SEND_EMAIL_MESSAGE;

@Controller
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public String contactsView(Model model) {
        if (!model.containsAttribute("contactData")) {
            model.addAttribute("contactData", contactService.getContactUs());
        }
        return "contacts";
    }

    @PostMapping
    public String contactUs(@Valid ContactUsDTO contactUsDTO, BindingResult bindingResult, RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("contactData", contactUsDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.contactData",
                    bindingResult);
            rAtt.addFlashAttribute("invalidData", true);
            return "redirect:/contacts";
        }
        contactService.contactUs(contactUsDTO);
        rAtt.addFlashAttribute("messageToDisplay", SUCCESSFULLY_SEND_EMAIL_MESSAGE);
        return "redirect:/";
    }
}
