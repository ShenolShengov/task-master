package bg.softuni.taskmaster.service.helpers;

import bg.softuni.taskmaster.exceptions.QuestionNotFoundException;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionHelperServiceImpl implements bg.softuni.taskmaster.service.QuestionHelperService {

    private QuestionRepository questionRepository;

    @Override
    public Question getById(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }
}
