package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.exceptions.UserNotFoundException;
import bg.softuni.taskmaster.model.dto.UserDetailsInfoDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.testutils.UserTestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserTestDataUtils userTestDataUtils;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = userTestDataUtils.saveTestUser("testUser", "test@com.me");
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
    }


    @Test
    @WithMockUser(username = "testUser")
    public void test_Delete() {
        assertEquals(1, userRepository.count());
        userService.delete(testUser.getId());
        assertEquals(0, userRepository.count());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void test_DeleteWith_NotValid_Id() {
        assertThrows(UserNotFoundException.class, () -> userService.delete(8L));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void test_GetAll_With_EmptySearchQuery() {
        addTestData();
        Page<UserDetailsInfoDTO> all = userService.getAll("", getPageable());
        assertEquals(5, all.getTotalElements());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void test_GetAll_With_Different_SearchQuery() {
        addTestData();
        assertEquals(1, userService.getAll("iv@com.me", getPageable()).getTotalElements());
        assertEquals(1, userService.getAll("Tomas", getPageable()).getTotalElements());
        assertEquals(1, userService.getAll("Nikolay Nikol", getPageable()).getTotalElements());
        assertEquals(2, userService.getAll("24", getPageable()).getTotalElements());
    }

    private Pageable getPageable() {
        return PageRequest.of(0, 10);
    }

    private void addTestData() {
        userTestDataUtils.saveTestUser("Tomas", "to@com.me");
        userTestDataUtils.saveTestUser("Ivan", "iv@com.me");
        userTestDataUtils.saveTestUser("Georgi", "ge@com.me", 24);
        userTestDataUtils.saveTestUser("Nikolay", "ni@com.me", "Nikolay Nikol", 24,
                false);
    }


}