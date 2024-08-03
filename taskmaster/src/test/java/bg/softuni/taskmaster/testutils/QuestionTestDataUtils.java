package bg.softuni.taskmaster.testutils;

import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QuestionTestDataUtils {

    private final QuestionRepository questionRepository;

    public QuestionTestDataUtils(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question saveTestQuestion(User user) {
        return questionRepository.save(getTestQuestion(user));
    }

    public Question saveTestQuestion(User user, String title, String tags, String description, String code) {
        return questionRepository.save(getTestQuestion(user, title, tags, description, code));
    }

    public static Question getTestQuestion(User user) {
        return getTestQuestion(user, "Test title", "tags", "desc", "code");
    }

    public static Question getTestQuestion(User user, String title, String tags, String description, String code) {
        return new Question(title, tags, description, code,
                new ArrayList<>(),
                user);
    }

    public void clearDB() {
        questionRepository.deleteAll();
    }
}
