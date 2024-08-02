package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Answer;
import bg.softuni.taskmaster.model.entity.Question;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.AnswerRepository;
import bg.softuni.taskmaster.utils.AnswerTestUtils;
import bg.softuni.taskmaster.utils.QuestionTestUtils;
import bg.softuni.taskmaster.utils.UserTestUtils;
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
    private UserTestUtils userTestUtils;

    @Autowired
    private QuestionTestUtils questionTestUtils;

    @Autowired
    private AnswerTestUtils answerTestUtils;
    private Question testQuestion;

    private Answer testAnswer;


    @BeforeEach
    void setUp() {
        User testUser = userTestUtils.getOrSaveTestUserFromDB("testUser", "test@gmail.com");
        testQuestion = questionTestUtils.saveTestQuestion(testUser);
        testAnswer = answerTestUtils.saveTestAnswer(testUser, testQuestion);
    }


    @AfterEach
    void tearDown() {
        answerTestUtils.clearDB();
        userTestUtils.clearDB();
        questionTestUtils.clearDB();
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
    @WithMockUser(value = "testUser")
    public void test_DeleteWithOtherUser() throws Exception {
        Answer answer = answerTestUtils.saveTestAnswer(
                userTestUtils.getOrSaveTestUserFromDB("otherUser", "otherUser@gmail.com"),
                testQuestion);

        long answersCountBeforeTryToDelete = answerRepository.count();
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/answers/{id}").build(answer.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
        assertEquals(answersCountBeforeTryToDelete, answerRepository.count());
    }

}