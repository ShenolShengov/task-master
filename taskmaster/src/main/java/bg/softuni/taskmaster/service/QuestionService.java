package bg.softuni.taskmaster.service;


import bg.softuni.taskmaster.model.dto.AskQuestionDTO;
import bg.softuni.taskmaster.model.dto.QuestionDetailsDTO;

public interface QuestionService {

    long ask(AskQuestionDTO askQuestionDTO);

    QuestionDetailsDTO getDetailsDTO(Long id);
}
