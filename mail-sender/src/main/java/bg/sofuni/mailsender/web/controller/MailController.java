package bg.sofuni.mailsender.web.controller;

import bg.sofuni.mailsender.dto.Payload;
import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.service.MailHistoryService;
import bg.sofuni.mailsender.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/hasHistory")
    public ResponseEntity<Boolean> hasHistory() {
        return ResponseEntity.ok(mailHistoryService.hasHistory());
    }

    @PostMapping("/send")
    public ResponseEntity<Payload> send(@RequestBody @Valid Payload payload,
                                        BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
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
