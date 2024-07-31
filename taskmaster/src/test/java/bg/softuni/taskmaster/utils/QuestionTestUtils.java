package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QuestionTestUtils {

    private static QuestionRepository questionRepository;


    public static Question getTestQuestion(User user, boolean persistToDB) {
        Question question = new Question("Test title", "tags", "desc", "code",
                new ArrayList<>(),
                user);
        if (!persistToDB) {
            return question;
        }

        return questionRepository.save(question);
    }

    public static void setQuestionRepository(QuestionRepository questionRepository) {
        QuestionTestUtils.questionRepository = questionRepository;
    }

    public static void clearDB() {
        questionRepository.deleteAll();
    }
}
