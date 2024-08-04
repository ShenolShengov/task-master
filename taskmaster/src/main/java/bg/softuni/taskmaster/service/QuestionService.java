package bg.softuni.taskmaster.service;


import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface QuestionService {

    long ask(QuestionAskDTO questionAskDTO);

    void delete(Long id);

    QuestionDetailsInfoDTO getDetailsInfo(Long id);

    Page<QuestionBaseInfoDTO> getFrom(LocalDate date, Pageable pageable);

    Page<QuestionBaseInfoDTO> getAll(String searchQuery, Pageable pageable);

    boolean isActualUser(Long id);

}
