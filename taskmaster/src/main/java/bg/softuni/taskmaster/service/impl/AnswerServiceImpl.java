package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.dto.AnswerDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.service.AnswerService;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final QuestionRepository questionRepository;

    @Override
    public void answer(AnswerDTO answerDTO, Long questionId) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);
        answer.setQuestion(questionRepository.findById(questionId).orElseThrow(RuntimeException::new));
        answer.setUser(userHelperService.getLoggedUser());
        answerRepository.save(answer);
    }
}
