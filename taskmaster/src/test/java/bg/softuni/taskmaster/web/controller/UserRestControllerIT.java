package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.UserRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerIT {

    public static final int NOT_VALID_USER_ID = 24;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTestDataUtils userTestDataUtils;

    private User testUser;

    private User testAdminUser;

    @BeforeEach
    void setUp() {
        testUser = userTestDataUtils.saveTestUser("testUser", "testUser@gmail.com");
        testAdminUser = userTestDataUtils.saveTestUser("testAdminUser", "testAdmin@gmail.com",
                true);
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
    }


    @Test
    @WithMockUser(username = "testAdminUser", roles = {"ADMIN", "USER"})
    public void test_MakeAdmin() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/make-admin/{id}")
                        .build(testUser.getId()))
                        .with(csrf()))
                .andExpect(status().isOk());
        Optional<User> optionalSavedUser = userRepository.findById(testUser.getId());
        assertTrue(optionalSavedUser.isPresent());
        User savedUser = optionalSavedUser.get();
        assertTrue(savedUser.getRoles().stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }

    @Test
    @WithMockUser(username = "testAdminUser", roles = {"ADMIN", "USER"})
    public void test_MakeAdminWithInvalidId() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/make-admin/{id}")
                        .build(24))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_MakeAdminWhenNotHaveAuthorities() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/make-admin/{id}")
                        .build(testUser.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testAdminUser", roles = {"ADMIN", "USER"})
    public void test_RemoveAdmin() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/remove-admin/{id}")
                        .build(testAdminUser.getId()))
                        .with(csrf()))
                .andExpect(status().isOk());
        Optional<User> optionalSavedUser = userRepository.findById(testAdminUser.getId());
        assertTrue(optionalSavedUser.isPresent());
        User savedUser = optionalSavedUser.get();
        assertTrue(savedUser.getRoles().stream().noneMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }


    @Test
    @WithMockUser(username = "testAdminUser", roles = {"ADMIN", "USER"})
    public void test_RemoveAdminWithInvalidId() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/remove-admin/{id}")
                        .build(24))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_RemoveAdminWhenNotHaveAuthorities() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/remove-admin/{id}")
                        .build(testUser.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN", "USER"})
    public void test_delete() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/users/api/{id}")
                        .build(testUser.getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        assertTrue(userRepository.findById(testUser.getId()).isEmpty());
    }


    @Test
    @WithMockUser(username = "testAdminUser", roles = {"ADMIN", "USER"})
    public void test_deleteWithInvalidId() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/users/api/{id}")
                        .build(NOT_VALID_USER_ID))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "otherTestUser")
    public void test_deleteWhenNotHaveAuthorities() throws Exception {
        userTestDataUtils.saveTestUser("otherTestUser", "other@gmail.com");
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/users/api/{id}")
                        .build(testUser.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


}