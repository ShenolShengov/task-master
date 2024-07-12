package bg.softuni.taskmaster.service;


import bg.softuni.taskmaster.model.dto.QuestionAskDTO;
import bg.softuni.taskmaster.model.dto.QuestionAnswerDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsInfoDTO;

public interface QuestionService {

    long ask(QuestionAskDTO questionAskDTO);

    QuestionDetailsInfoDTO getDetailsInfoDTO(Long id);
    QuestionDetailsInfoDTO getBaseInfoDTO(Long id);

    void answer(QuestionAnswerDTO questionAnswerDTO, Long id);
}
