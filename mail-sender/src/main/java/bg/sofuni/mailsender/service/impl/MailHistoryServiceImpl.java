package bg.sofuni.mailsender.service.impl;

import bg.sofuni.mailsender.dto.MailHistoryInfoDTO;
import bg.sofuni.mailsender.enity.MailHistory;
import bg.sofuni.mailsender.repository.MailHistoryRepository;
import bg.sofuni.mailsender.service.MailHistoryService;
import bg.sofuni.mailsender.utils.InstantUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MailHistoryServiceImpl implements MailHistoryService {

    private final MailHistoryRepository mailHistoryRepository;
    private final ModelMapper modelMapper;

    @Value("${history.retention.period}")
    private Period retentionPeriod;


    @Override
    public Page<MailHistoryInfoDTO> history(String filterByDate, Pageable pageable) {
        if (filterByDate.equals("all")) {
            return mailHistoryRepository.findAll(pageable).map(toMailHistoryInfoDTO());
        }
        Instant date = InstantUtils.toInstant(filterByDate);
        if (filterByDate.equals("today") || filterByDate.equals("yesterday")) {
            return mailHistoryRepository.findAllByDateEquals(date, pageable).map(toMailHistoryInfoDTO());
        }
        return mailHistoryRepository.findAllByDateGreaterThanEqual(date, pageable).map(toMailHistoryInfoDTO());
    }

    private Function<MailHistory, MailHistoryInfoDTO> toMailHistoryInfoDTO() {
        return e -> modelMapper.map(e, MailHistoryInfoDTO.class);
    }

    @Override
    public void deleteOldHistory() {
        Instant deleteBefore = Instant.now().minus(retentionPeriod);
        mailHistoryRepository.deleteOldHistory(deleteBefore);
    }

    @Override
    public void deleteHistory() {
        mailHistoryRepository.deleteAll();
    }

    @Override
    public Boolean hasHistory() {
        return mailHistoryRepository.count() > 0;
    }
}
