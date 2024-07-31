package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.repository.QuestionRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private QuestionRepository questionRepository;


    private User mockAdminUser;

    private Question testQuestion;
    private Answer testAnswer;

    @BeforeEach
    void setUp() {
        User mockUser = userRepository.save(getTestUser());
        mockAdminUser = userRepository.save(getTestUser().
                setEmail("mockAdminUser@gmail.com").setUsername("mockAdminUser"));
        testQuestion = questionRepository.save(
                new Question("title", "tags", "desc", "code", new ArrayList<>(), mockUser));
        testAnswer = answerRepository.save(new Answer("desc", "code", mockUser, testQuestion));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    @WithMockUser("mockUser")
    public void test_DeleteWithActualUser() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/answers/{id}").build(testAnswer.getId()))
                        .with(csrf()))
                .andExpect(status().isNoContent());
        assertEquals(0, answerRepository.count());
        assertEquals(0, testQuestion.getAnswers().size());
    }

    @Test
    @WithMockUser(value = "mockUser")
    public void test_DeleteWithOtherUser() throws Exception {
        Answer answer = answerRepository.save(new Answer("desc", "code", mockAdminUser, testQuestion));
        long answersCountBeforeTryToDelete = answerRepository.count();
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/answers/{id}").build(answer.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
        assertEquals(answersCountBeforeTryToDelete, answerRepository.count());
    }


    private User getTestUser() {
        return new User("mockUser", "Mock mock", "mockUser@gmail.com",
                20, "pass", Set.of(), Set.of(), Set.of(), Set.of(), null);
    }

}