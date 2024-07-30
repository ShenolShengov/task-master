package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.PictureRepository;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static bg.softuni.taskmaster.model.enums.TaskPriorities.HIGH;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private TaskRepository taskRepository;
    private Task testTask;

    @BeforeEach
    void setUp() {
        Picture profilePicture = addDefaultProfilePicture();
        User saveUser = userRepository.save(getTestUser(profilePicture));
        testTask = taskRepository.save(new Task("test",
                "category", HIGH, LocalDate.now(), LocalTime.now(),
                LocalTime.now().plusHours(1), false, "desc", saveUser));

        userRepository.save(getTestUser(profilePicture)
                .setUsername("otherMockUser").setEmail("other@gmail.com"));
        userRepository.save(getTestUser(profilePicture)
                .setUsername("mockAdminUser").setEmail("admin@gmail.com"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        pictureRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_addTaskView() throws Exception {
        mockMvc.perform(get("/tasks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-task"))
                .andExpect(model().attributeExists("addTaskData"));
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_DoAddTaskWithValidDTO() throws Exception {
        taskRepository.deleteAll();
        mockMvc.perform(post("/tasks/add")
                        .with(csrf())
                        .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(getTestAddTaskFormFields()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        assertEquals(1, taskRepository.count());
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_DoAddTaskWithInValidDTO() throws Exception {
        taskRepository.deleteAll();
        mockMvc.perform(post("/tasks/add")
                        .with(csrf())
                        .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(new LinkedMultiValueMap<>()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/tasks/add"))
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("addTaskData",
                        "org.springframework.validation.BindingResult.addTaskData"));
        assertEquals(0, taskRepository.count());
    }


    @Test
    @WithMockUser("otherMockUser")
    public void test_EditTaskWithOtherUser() throws Exception {
        mockMvc.perform(get(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser("mockUser")
    public void test_EditTaskWithCorrectUser() throws Exception {
        mockMvc.perform(get(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-task"));
    }

    @Test
    @WithMockUser(value = "mockAdminUser", roles = {"USER", "ADMIN"})
    public void test_EditTaskWithAdminUser() throws Exception {
        mockMvc.perform(get(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-task"));
    }

    @Test
    @WithMockUser(username = "otherMockUser")
    public void test_Do_EditTaskWithOtherUser() throws Exception {
        mockMvc.perform(post(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser("mockUser")
    public void test_Do_EditTaskWithCorrectUserButWrongData() throws Exception {
        mockMvc.perform(post(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()))
                        .with(csrf())
                        .formFields(new LinkedMultiValueMap<>()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()).toString()))
                .andExpect(flash().attributeCount(3))
                .andExpect(flash().attributeExists("taskData", "haveData",
                        "org.springframework.validation.BindingResult.taskData"));
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_Do_EditTaskWithCorrectUser() throws Exception {
        mockMvc.perform(post(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()))
                        .formFields(getTestAddTaskFormFields())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        Optional<Task> optionalEditedTask = taskRepository.findById(testTask.getId());
        assertTrue(optionalEditedTask.isPresent());
        Task editedTask = optionalEditedTask.get();
        assertTaskIsEdited(editedTask);
    }

    @Test
    @WithMockUser(value = "mockAdminUser", roles = {"USER", "ADMIN"})
    public void test_Do_EditTaskWithAdminUser() throws Exception {
        mockMvc.perform(post(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()))
                        .formFields(getTestAddTaskFormFields())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        Optional<Task> optionalEditedTask = taskRepository.findById(testTask.getId());
        assertTrue(optionalEditedTask.isPresent());
        Task editedTask = optionalEditedTask.get();
        assertTaskIsEdited(editedTask);
    }

    @Test
    @WithMockUser(username = "otherMockUser")
    public void test_DeleteWithOtherUser() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/tasks/{id}").build(testTask.getId()))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        assertEquals(1, taskRepository.count());
    }

    @Test
    @WithMockUser(username = "mockUser")
    public void test_DeleteWithCorrectUser() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/tasks/{id}").build(testTask.getId()))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        assertEquals(0, taskRepository.count());
    }

    @Test
    @WithMockUser(username = "mockAdminUser", roles = {"USER", "ADMIN"})
    public void test_DeleteWithAdminUser() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/tasks/{id}").build(testTask.getId()))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        assertEquals(0, taskRepository.count());
    }

    private void assertTaskIsEdited(Task editedTask) {
        assertNotEquals(testTask.getName(), editedTask.getName());
        assertNotEquals(testTask.getCategory(), editedTask.getCategory());
        assertNotEquals(testTask.getPriority(), editedTask.getPriority());
        assertNotEquals(testTask.getDueDate(), editedTask.getDueDate());
        assertNotEquals(testTask.getStartTime(), editedTask.getStartTime());
        assertNotEquals(testTask.getEndTime(), editedTask.getEndTime());
        assertEquals(testTask.isAllDay(), editedTask.isAllDay());
        assertNotEquals(testTask.getDescription(), editedTask.getDescription());
    }

    private MultiValueMap<String, String> getTestAddTaskFormFields() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Name", "Test task");
        map.add("category", "Personal");
        map.add("priority", "LOW");
        map.add("dueDate", LocalDate.now().plusDays(1).toString());
        map.add("startTime", testTask.getStartTime().plusMinutes(27).toString());
        map.add("endTime", testTask.getEndTime().plusMinutes(30).toString());
        map.add("allDay", "false");
        map.add("description", "Test desc");
        return map;
    }

    private Picture addDefaultProfilePicture() {
        return pictureRepository.
                save(new Picture("default", "defUrl", "defUrlId"));
    }

    private static User getTestUser(Picture profilePicture) {
        return new User("mockUser", "test", "test@gmail", 2, "pass",
                Set.of(), Set.of(), Set.of(), Set.of(), profilePicture);
    }
}