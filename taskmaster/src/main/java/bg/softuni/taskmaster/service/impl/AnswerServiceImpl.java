package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.events.AnswerToQuestionEvent;
import bg.softuni.taskmaster.model.dto.AnswerDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.service.AnswerService;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final QuestionRepository questionRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public void answer(AnswerDTO answerDTO, Long questionId) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);
        Question question = questionRepository.findById(questionId).orElseThrow(RuntimeException::new);
        User loggedUser = userHelperService.getLoggedUser();
        answer.setQuestion(question);
        answer.setUser(loggedUser);
        answerRepository.save(answer);
        publisher.publishEvent(new AnswerToQuestionEvent(this, question.getUser().getEmail(),
                question.getUser().getUsername(), question.getTitle(), getLinkFor(questionId), loggedUser.getUsername()));
    }

    private String getLinkFor(Long questionId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/questions/{id}")
                .buildAndExpand(questionId).toUriString();
    }
    //todo

    @Override
    public boolean isActualUserOrAdmin(Long id) {
        boolean isPresent = answerRepository.findById(id)
                .filter(e -> e.getUser().getId().equals(userHelperService.getLoggedUser().getId()))
                .isPresent();
        return isPresent || userHelperService.isAdmin();
    }

    @Override
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }
}
