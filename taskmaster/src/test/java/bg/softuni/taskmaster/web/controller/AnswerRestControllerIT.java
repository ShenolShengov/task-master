package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.testutils.AnswerTestDataUtils;
import bg.softuni.taskmaster.testutils.QuestionTestDataUtils;
import bg.softuni.taskmaster.testutils.UserTestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private UserTestDataUtils userTestDataUtils;

    @Autowired
    private QuestionTestDataUtils questionTestDataUtils;

    @Autowired
    private AnswerTestDataUtils answerTestDataUtils;
    private Question testQuestion;

    private Answer testAnswer;


    @BeforeEach
    void setUp() {
        User testUser = userTestDataUtils.saveTestUser("testUser", "test@gmail.com");
        testQuestion = questionTestDataUtils.saveTestQuestion(testUser);
        testAnswer = answerTestDataUtils.saveTestAnswer(testUser, testQuestion);
    }


    @AfterEach
    void tearDown() {
        answerTestDataUtils.clearDB();
        userTestDataUtils.clearDB();
        questionTestDataUtils.clearDB();
    }


    @Test
    @WithMockUser("testUser")
    public void test_DeleteWithActualUser() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/answers/{id}").build(testAnswer.getId()))
                        .with(csrf()))
                .andExpect(status().isNoContent());
        assertEquals(0, answerRepository.count());
        assertEquals(0, testQuestion.getAnswers().size());
    }

    @Test
    @WithMockUser("testUser")
    public void test_DeleteWithOtherUser() throws Exception {
        Answer answer = answerTestDataUtils.saveTestAnswer(
                userTestDataUtils.saveTestUser("otherUser", "otherUser@gmail.com"),
                testQuestion);

        long answersCountBeforeTryToDelete = answerRepository.count();
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/answers/{id}").build(answer.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
        assertEquals(answersCountBeforeTryToDelete, answerRepository.count());
    }

    @Test
    @WithMockUser("testUser")
    public void test_WithInvalid_Id() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/answers/{id}").build(-4L))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}