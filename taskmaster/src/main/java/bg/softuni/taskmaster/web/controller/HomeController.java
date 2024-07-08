package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.dto.ContactUsDTO;
import bg.softuni.taskmaster.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ContactService contactService;
    @GetMapping("/")
    public String indexView() {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return "index";
        }
        return "home";
    }

    @GetMapping("/home")
    public String homeView() {
        return "home";
    }

    @GetMapping("/about")
    public String aboutView() {
        return "about";
    }

    @GetMapping("/contacts")
    public String contactsView(Model model) {
        if (!model.containsAttribute("contactData")) {
            model.addAttribute("contactData", new ContactUsDTO());
        }
        return "contacts";
    }

    @PostMapping("/contacts")
    public String doContacts(@Valid ContactUsDTO contactUsDTO, BindingResult bindingResult, RedirectAttributes rAtt) {
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("contactData", contactUsDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.contactData",
                    bindingResult);
            rAtt.addFlashAttribute("scrollToFrom", true);
            return "redirect:/contacts";
        }
        contactService.sendMail(contactUsDTO);
        rAtt.addFlashAttribute("mailSent", true);
        return "redirect:/";
    }
}
