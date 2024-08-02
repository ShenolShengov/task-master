package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QuestionTestUtils {

    private final QuestionRepository questionRepository;

    public QuestionTestUtils(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public Question saveTestQuestion(User user) {
        return questionRepository.save(getTestQuestion(user));
    }

    public static Question getTestQuestion(User user) {
        return new Question("Test title", "tags", "desc", "code",
                new ArrayList<>(),
                user);
    }

    public void clearDB() {
        questionRepository.deleteAll();
    }
}
