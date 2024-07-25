package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import bg.sofuni.mailsender.service.MailHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailHistoryServiceImpl implements MailHistoryService {

    private final MailHistoryRepository mailHistoryRepository;

    @Override
    public Page<MailHistory> history(Pageable pageable) {
        return mailHistoryRepository.findAll(pageable);
    }
}
