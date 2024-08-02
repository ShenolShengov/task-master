package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.utils.UserTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTestUtils userTestUtils;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        userTestUtils.getOrSaveTestUserFromDB("mockUser", "mock@gmail.com", true);
    }

    @AfterEach
    void tearDown() {
        userTestUtils.clearDB();
    }

    @Test
    @WithMockUser(value = "mockUser", roles = {"USER", "ADMIN"})
    public void test_CloseAccount() throws Exception {
        mockMvc.perform(delete("/users/close-account")
                        .with(csrf()))
                .andExpect(status().isFound());
        assertEquals(0, userRepository.count());
    }

    @Test
    @WithMockUser(value = "mockUser", roles = {"USER", "ADMIN"})
    public void test_GetAll_With_Invalid_Sort() throws Exception {
        mockMvc.perform(get("/users")
                        .queryParam("sort", "wrongSort"))
                .andExpect(status().isFound())
                .andExpect(model().attributeDoesNotExist("sortProperties", "sortDirection", "foundedUsers"));
    }

    @Test
    @WithMockUser(value = "mockUser", roles = {"USER", "ADMIN"})
    public void test_GetAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sortProperties", "sortDirection", "foundedUsers"));
    }

}