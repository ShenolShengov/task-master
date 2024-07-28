package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.PictureRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

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
    private PictureRepository pictureRepository;

    @Autowired
    private UserHelperService helperService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Picture profilePicture = pictureRepository.save(new Picture("default", "defaultUrl",
                "defaultPublicId"));
        userRepository.save(new User("mockUser", "MockUser", "mock@gmail.com",
                20, "password", Set.of(), Set.of(), Set.of(), Set.of(),
                profilePicture));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "mockUser")
    public void test_CloseAccount() throws Exception {
        mockMvc.perform(delete("/users/close-account")
                        .with(csrf()))
                .andExpect(status().isFound());
        assertEquals(0, userRepository.count());
    }

    @Test
    @WithMockUser(value = "mockUser", roles = "ADMIN")
    public void test_GetAll_With_Invalid_Sort() throws Exception {
        mockMvc.perform(get("/users")
                        .queryParam("sort", "wrongSort"))
                .andExpect(status().isFound())
                .andExpect(model().attributeDoesNotExist("sortProperties", "sortDirection", "foundedUsers"));
    }

    @Test
    @WithMockUser(value = "mockUser", roles = "ADMIN")
    public void test_GetAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sortProperties", "sortDirection", "foundedUsers"));
    }

}