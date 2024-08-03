package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.testutils.QuestionTestDataUtils;
import bg.softuni.taskmaster.testutils.TaskTestDataUtils;
import bg.softuni.taskmaster.testutils.UserTestDataUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class StatisticsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestDataUtils userTestDataUtils;

    @Autowired
    private TaskTestDataUtils taskTestDataUtils;

    @Autowired
    private QuestionTestDataUtils questionTestDataUtils;

    @BeforeEach
    void setUp() {
        User testUser = userTestDataUtils.saveTestUser("testUser", "test@gmail.com", true);
        User otherUser = userTestDataUtils.saveTestUser("otherUser", "othr@com.me", false);
        User anotherUser = userTestDataUtils.saveTestUser("anotherUser", "a@a.com", false);
        taskTestDataUtils.saveTestTask(testUser);
        taskTestDataUtils.saveTestTask(testUser);
        taskTestDataUtils.saveTestTask(otherUser);
        taskTestDataUtils.saveTestTask(anotherUser);
        questionTestDataUtils.saveTestQuestion(testUser);
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
        taskTestDataUtils.clearDB();
        questionTestDataUtils.clearDB();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void test_StatisticsView() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("statisticsData", hasProperty("usersCount",
                        Matchers.equalTo(3L))))
                .andExpect(model().attribute("statisticsData", hasProperty("adminUsersCount",
                        Matchers.equalTo(1L))))
                .andExpect(model().attribute("statisticsData", hasProperty("regularUsersCount",
                        Matchers.equalTo(2L))))
                .andExpect(model().attribute("statisticsData", hasProperty("tasksCount",
                        Matchers.equalTo(4L))))
                .andExpect(model().attribute("statisticsData", hasProperty("questionsCount",
                        Matchers.equalTo(1L))))
                .andExpect(model().attribute("statisticsData", hasProperty("answersCount",
                        Matchers.equalTo(0L))));
    }

}