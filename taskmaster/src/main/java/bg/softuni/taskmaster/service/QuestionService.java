package bg.softuni.taskmaster.service;


import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionBaseInfoDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;
import bg.softuni.taskmaster.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface QuestionService {

    long ask(QuestionAskDTO questionAskDTO);
    void answer(QuestionAnswerDTO questionAnswerDTO, Long id);

    QuestionDetailsInfoDTO getDetailsInfoDTO(Long id);
    Page<QuestionBaseInfoDTO> getQuestionsFrom(LocalDate questionCreatedTime, Pageable pageable);
    QuestionBaseInfoDTO toBaseInfo(Question question);
    Page<QuestionBaseInfoDTO> getAll(String searchQuery, Pageable pageable);
}
