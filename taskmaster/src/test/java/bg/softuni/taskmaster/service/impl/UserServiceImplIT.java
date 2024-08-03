package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.exceptions.UserNotFoundException;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
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
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceImplIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserTestDataUtils userTestDataUtils;
    private User testUser;

//    @MockBean
//    private MailService mockMailService;

    @BeforeEach
    void setUp() {
        testUser = userTestDataUtils.getOrSaveTestUserFromDB("testUser", "test@com.me");
//        doNothing().when(mockMailService).send(any(Payload.class));
    }

    @AfterEach
    void tearDown() {
        userTestDataUtils.clearDB();
    }

    @Test
    void test_GetInfoWith_Valid_Id() {
        UserInfoDTO info = userService.getInfo(testUser.getId());
        assertEquals(testUser.getId(), info.getId());
        assertEquals(testUser.getUsername(), info.getUsername());
        assertEquals(testUser.getFullName(), info.getFullName());
        assertEquals(testUser.getAge(), info.getAge());
        assertEquals(testUser.getEmail(), info.getEmail());
        assertFalse(info.isAdmin());
    }

    @Test
    void test_GetInfoWith_NotValid_Id() {
        assertThrows(UserNotFoundException.class, () -> userService.getInfo(-4L));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void test_deleteWith_Valid_Id() {
        assertEquals(1, userRepository.count());
        userService.delete(testUser.getId());
        assertEquals(0, userRepository.count());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void test_deleteWith_NotValid_Id() {
        assertThrows(UserNotFoundException.class, () -> userService.delete(8L));
    }


    @Test
    public void test_Exist() {
        assertTrue(userService.exists(testUser.getId()));
        assertFalse(userService.exists(7L));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void test_getAll_With_EmptySearchQuery() {
        addTestData();
        Page<UserInfoDTO> all = userService.getAll("", getPageable());
        assertEquals(5, all.getTotalElements());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void test_getAll_With_NotEmptySearchQuery() {
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
        userTestDataUtils.getOrSaveTestUserFromDB("Tomas", "to@com.me");
        userTestDataUtils.getOrSaveTestUserFromDB("Ivan", "iv@com.me");
        userTestDataUtils.getOrSaveTestUserFromDB("Georgi", "ge@com.me", 24);
        userTestDataUtils.getOrSaveTestUserFromDB("Nikolay", "ni@com.me", "Nikolay Nikol", 24,
                false);
    }


}