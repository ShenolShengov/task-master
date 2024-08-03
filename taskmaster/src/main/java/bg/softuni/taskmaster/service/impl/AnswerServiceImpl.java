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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static bg.softuni.taskmaster.utils.EmailUtils.LINK_TO_QUESTION;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final QuestionRepository questionRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public void answer(AnswerDTO answerDTO, Long questionId) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);
        Question question = questionRepository.getReferenceById(questionId);
        User loggedUser = userHelperService.getLoggedUser();
        answer.setQuestion(question);
        answer.setUser(loggedUser);
        answerRepository.save(answer);
        publisher.publishEvent(new AnswerToQuestionEvent(this, question.getUser().getEmail(),
                question.getUser().getUsername(), question.getTitle(),
                getLinkFor(questionId), loggedUser.getUsername()));
    }

    private String getLinkFor(Long questionId) {
        return String.format(LINK_TO_QUESTION, questionId);
    }

    @Override
    public boolean isActualUser(Long id) {
        return answerRepository.findById(id)
                .filter(e -> e.getUser().getUsername().equals(userHelperService.getUsername()))
                .isPresent();
    }

    @Override
    @PreAuthorize("@answerServiceImpl.isActualUser(#id) || hasRole('ADMIN')")
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public boolean exist(Long id) {
        return answerRepository.existsById(id);
    }
}
