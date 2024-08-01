package bg.softuni.taskmaster.web.controller;

import bg.softuni.taskmaster.model.entity.Task;
import bg.softuni.taskmaster.repository.TaskRepository;
import bg.softuni.taskmaster.utils.TaskTestUtils;
import bg.softuni.taskmaster.utils.UserTestUtils;
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
import java.util.Optional;

import static bg.softuni.taskmaster.utils.TaskTestUtils.getTestTask;
import static bg.softuni.taskmaster.utils.UserTestUtils.getOrSaveTestUserFromDB;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;
    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = getTestTask(getOrSaveTestUserFromDB("testUser", "mock@gmail.com"), true);
        getOrSaveTestUserFromDB("otherTestUser", "other@gmail.com");
    }

    @AfterEach
    void tearDown() {
        UserTestUtils.clearDB();
        TaskTestUtils.clearDB();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_addTaskView() throws Exception {
        mockMvc.perform(get("/tasks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-task"))
                .andExpect(model().attributeExists("addTaskData"));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_DoAddTaskWithValidDTO() throws Exception {
        taskRepository.deleteAll();
        mockMvc.perform(post("/tasks/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(getTestAddTaskDTOFormFields()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        assertEquals(1, taskRepository.count());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_DoAddTaskWithInValidDTO() throws Exception {
        taskRepository.deleteAll();
        mockMvc.perform(post("/tasks/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formFields(new LinkedMultiValueMap<>()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/tasks/add"))
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("addTaskData",
                        "org.springframework.validation.BindingResult.addTaskData"));
        assertEquals(0, taskRepository.count());
    }


    @Test
    @WithMockUser("otherTestUser")
    public void test_EditTaskWithOtherUser() throws Exception {
        mockMvc.perform(get(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("testUser")
    public void test_EditTaskWithCorrectUser() throws Exception {
        mockMvc.perform(get(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-task"));
    }

    @Test
    @WithMockUser(username = "otherTestUser")
    public void test_Do_EditTaskWithOtherUser() throws Exception {
        mockMvc.perform(post(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("testUser")
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
    @WithMockUser(username = "testUser")
    public void test_Do_EditTaskWithCorrectUser() throws Exception {
        mockMvc.perform(post(ServletUriComponentsBuilder
                        .fromPath("/tasks/edit/{id}").build(testTask.getId()))
                        .formFields(getTestAddTaskDTOFormFields())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
        Optional<Task> optionalEditedTask = taskRepository.findById(testTask.getId());
        assertTrue(optionalEditedTask.isPresent());
        Task editedTask = optionalEditedTask.get();
        assertTaskIsEdited(editedTask);
    }

    @Test
    @WithMockUser(username = "otherTestUser")
    public void test_DeleteWithOtherUser() throws Exception {
        mockMvc.perform(delete(ServletUriComponentsBuilder
                        .fromPath("/tasks/{id}").build(testTask.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
        assertEquals(1, taskRepository.count());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_DeleteWithCorrectUser() throws Exception {
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

    private MultiValueMap<String, String> getTestAddTaskDTOFormFields() {
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
}