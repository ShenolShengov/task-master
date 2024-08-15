package bg.softuni.taskmaster.service.helpers;

import bg.softuni.taskmaster.exceptions.AnswerNotFoundException;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.service.AnswerHelperService;
import bg.softuni.taskmaster.service.UserHelperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AnswerHelperServiceImpl implements AnswerHelperService {

    private final AnswerRepository answerRepository;
    private final UserHelperService userHelperService;

    @Override
    public boolean isActualUser(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
        return answer.getUser().getUsername().equals(userHelperService.getUsername());
    }
}
