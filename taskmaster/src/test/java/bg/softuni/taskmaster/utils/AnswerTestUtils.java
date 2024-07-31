package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.dto.AnswerDTO;
import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import org.springframework.stereotype.Component;

@Component
public class AnswerTestUtils {

    private static AnswerRepository answerRepository;

    public static Answer getTestAnswer(User user, Question question, boolean persisToDB) {

        Answer answer = new Answer("Test desc", "code", user, question);
        if (!persisToDB) {
            return answer;
        }

        return answerRepository.save(answer);
    }

    public static void setAnswerRepository(AnswerRepository answerRepository) {
        AnswerTestUtils.answerRepository = answerRepository;
    }

    public static AnswerDTO getTestAnswerDTO() {
        return new AnswerDTO("Test desc", "testCode");
    }

    public static void clearDB() {
        answerRepository.deleteAll();
    }
}
