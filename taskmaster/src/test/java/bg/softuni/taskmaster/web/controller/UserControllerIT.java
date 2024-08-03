package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.testutils.UserTestDataUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {


    public static final String TEST_SORT_PROPERTIES = "age";
    public static final String TEST_SORT_DIRECTION = "desc";
    private static final String TEST_USER_SEARCH_QUERY = "Test test";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTestDataUtils userTestDataUtils;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        userTestDataUtils.saveTestUser("mockUser", "mock@gmail.com", true);
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"USER", "ADMIN"})
    public void test_CloseAccount() throws Exception {
        mockMvc.perform(delete("/users/close-account")
                        .with(csrf()))
                .andExpect(status().isFound());
        assertEquals(0, userRepository.count());
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"USER", "ADMIN"})
    public void test_GetAll_With_Invalid_Sort() throws Exception {
        mockMvc.perform(get("/users")
                        .queryParam("sort", "wrongSort"))
                .andExpect(status().isFound())
                .andExpect(model().attributeDoesNotExist("sortProperties", "sortDirection", "foundedUsers"));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"USER", "ADMIN"})
    public void test_GetAll() throws Exception {
        addTestUsers();
        mockMvc.perform(get("/users")
                        .param("page", "1")
                        .param("sort", TEST_SORT_PROPERTIES + "," + TEST_SORT_DIRECTION)
                        .param("search_query", TEST_USER_SEARCH_QUERY))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("sortProperties", "sortDirection",
                        "searchQuery", "foundedUsers"))
                .andExpect(model().attribute("searchQuery", Matchers.equalTo(TEST_USER_SEARCH_QUERY)))
                .andExpect(model().attribute("sortProperties", Matchers.equalTo(TEST_SORT_PROPERTIES)))
                .andExpect(model().attribute("sortDirection", Matchers.equalTo(TEST_SORT_DIRECTION)))
                .andExpect(model().attribute("foundedUsers",
                        hasProperty("content", Matchers.hasSize(3))))
                .andExpect(model().attribute("foundedUsers",
                        hasProperty("content", hasItem(
                                allOf(
                                        hasProperty("fullName", equalTo(TEST_USER_SEARCH_QUERY))
                                )
                        ))));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"USER", "ADMIN"})
    public void test_GetAll_With_NotValidSort() throws Exception {
        addTestUsers();
        mockMvc.perform(get("/users")
                        .param("page", "1")
                        .param("sort", TEST_SORT_PROPERTIES + "mess" + "," + TEST_SORT_DIRECTION)
                        .param("search_query", TEST_USER_SEARCH_QUERY))
                .andExpect(status().isFound());
    }

    private void addTestUsers() {
        userTestDataUtils.saveTestUser("testUserAgain", "ttA@me.com", "Test test");
        userTestDataUtils.saveTestUser("otherTestUser", "oth@me.com", "Test test");
        userTestDataUtils.saveTestUser("anotherTestUser", "an@m.com", "Test test");
    }

}