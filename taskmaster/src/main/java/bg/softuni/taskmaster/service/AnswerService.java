package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.dto.AnswerDTO;

public interface AnswerService {

    void answer(AnswerDTO answerDTO, Long questionId);

    boolean isActualUser(Long id);

    void delete(Long id);

    boolean exist(Long id);
}
