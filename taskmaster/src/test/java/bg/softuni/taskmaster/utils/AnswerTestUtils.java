package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import org.springframework.stereotype.Component;

@Component
public class AnswerTestUtils {

    private final AnswerRepository answerRepository;

    public AnswerTestUtils(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public static Answer getTestAnswer(User user, Question question) {
        return new Answer("Test desc", "code", user, question);
    }

    public Answer saveTestAnswer(User user, Question question) {
        return answerRepository.save(getTestAnswer(user, question));
    }


    public void clearDB() {
        answerRepository.deleteAll();
    }
}
