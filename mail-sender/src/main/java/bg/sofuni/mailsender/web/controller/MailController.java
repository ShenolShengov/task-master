package bg.sofuni.mailsender.web.controller;

import bg.sofuni.mailsender.dto.Payload;
import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.service.MailHistoryService;
import bg.sofuni.mailsender.service.MailService;
import bg.sofuni.mailsender.utils.UrlUtils;
import bg.sofuni.mailsender.web.ErrorInfo;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.exceptions.TemplateAssertionException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MailHistoryService mailHistoryService;


    @GetMapping("/history")
    public ResponseEntity<PagedModel<MailHistory>> mailHistory(@RequestParam(required = false) Integer ignoredPage,
                                                               @RequestParam(required = false) String ignoredSort,
                                                               @RequestParam(required = false, defaultValue = "today")
                                                               String filterByDate,
                                                               @PageableDefault(sort = "date", direction = Sort.Direction.DESC)
                                                               Pageable pageable
    ) {
        return ResponseEntity.ok(new PagedModel<>(mailHistoryService.history(filterByDate, pageable)));
    }

    @ExceptionHandler({PathElementException.class, InvalidDataAccessApiUsageException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleExceptionOnFiltering() {
        String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        return new ErrorInfo(url, HttpStatus.BAD_REQUEST, "Invalid sort property");
    }


    @GetMapping("/hasHistory")
    public ResponseEntity<Boolean> hasHistory() {
        return ResponseEntity.ok(mailHistoryService.hasHistory());
    }

    @ExceptionHandler(TemplateAssertionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleExceptionOnSend() {
        return new ErrorInfo(UrlUtils.currentUrl(), HttpStatus.BAD_REQUEST, "Invalid params");
    }

    @PostMapping("/send")
    public ResponseEntity<Payload> send(@RequestBody @Valid Payload payload,
                                        BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().build();
        }
        mailService.send(payload);
        return ResponseEntity.ok(payload);
    }


    @DeleteMapping("/history")

    public ResponseEntity<Void> deleteHistory() {
        mailHistoryService.deleteHistory();
        return ResponseEntity.noContent().build();
    }
}
