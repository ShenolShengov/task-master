package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerIT {


    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
    public void test_MakeAdmin() throws Exception {
        Long savedUserId = userRepository.save(getTestUserWithoutAdminRole()).getId();
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/make-admin/{id}")
                        .build(savedUserId))
                        .with(csrf()))
                .andExpect(status().isOk());
        Optional<User> optionalSavedUser = userRepository.findById(savedUserId);
        assertTrue(optionalSavedUser.isPresent());
        User savedUser = optionalSavedUser.get();
        assertTrue(savedUser.getRoles().stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
    public void test_MakeAdminWithInvalidId() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/make-admin/{id}")
                        .build(24))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_MakeAdminWhenNotHaveAuthorities() throws Exception {
        Long savedUserId = userRepository.save(getTestUserWithoutAdminRole()).getId();
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/make-admin/{id}")
                        .build(savedUserId))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
    public void test_RemoveAdmin() throws Exception {
        Long savedUserId = userRepository.save(getTestUserWithAdminRole()).getId();
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/remove-admin/{id}")
                        .build(savedUserId))
                        .with(csrf()))
                .andExpect(status().isOk());
        Optional<User> optionalSavedUser = userRepository.findById(savedUserId);
        assertTrue(optionalSavedUser.isPresent());
        User savedUser = optionalSavedUser.get();
        assertTrue(savedUser.getRoles().stream().noneMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }


    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
    public void test_RemoveAdminWithInvalidId() throws Exception {
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/remove-admin/{id}")
                        .build(24))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_RemoveAdminWhenNotHaveAuthorities() throws Exception {
        Long savedUserId = userRepository.save(getTestUserWithoutAdminRole()).getId();
        mockMvc.perform(patch(ServletUriComponentsBuilder
                        .fromPath("/users/api/remove-admin/{id}")
                        .build(savedUserId))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
    public void test_delete() throws Exception {
        Long savedUserId = userRepository.save(getTestUserWithAdminRole()).getId();
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/users/api/{id}")
                        .build(savedUserId))
                        .with(csrf()))
                .andExpect(status().isOk());

        assertTrue(userRepository.findById(savedUserId).isEmpty());
    }


    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
    public void test_deleteWithInvalidId() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/users/api/{id}")
                        .build(24))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_deleteWhenNotHaveAuthorities() throws Exception {
        Long savedUserId = userRepository.save(getTestUserWithoutAdminRole()).getId();
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/users/api/{id}")
                        .build(savedUserId))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }


    private User getTestUserWithAdminRole() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.getByName(UserRoles.ADMIN));
        roles.add(roleRepository.getByName(UserRoles.USER));
        return new User("Test", "Test Testing", "test@gmail.com",
                20, "pass", roles, Set.of(), Set.of(), Set.of(), null);
    }

    private User getTestUserWithoutAdminRole() {
        User user = getTestUserWithAdminRole();
        user.getRoles().removeIf(e -> e.getName().equals(UserRoles.ADMIN));
        return user;
    }
}