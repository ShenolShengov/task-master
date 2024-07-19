package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.AnswerDTO;

public interface AnswerService {

    void answer(AnswerDTO answerDTO, Long questionId);
}
