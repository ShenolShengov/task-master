package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.repository.*;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class StaticContextInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final TaskRepository taskRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public StaticContextInitializer(RoleRepository roleRepository, UserRepository userRepository, PictureRepository pictureRepository, TaskRepository taskRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.taskRepository = taskRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostConstruct
    public void init() {
        RoleTestUtils.setRoleRepository(roleRepository);
        UserTestUtils.setUserRepository(userRepository);
        PictureTestUtils.setPictureRepository(pictureRepository);
        QuestionTestUtils.setQuestionRepository(questionRepository);
        AnswerTestUtils.setAnswerRepository(answerRepository);
        TaskTestUtils.setTaskRepository(taskRepository);
    }
}
