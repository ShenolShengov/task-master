package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.exceptions.UserNotFoundException;
import bg.softuni.taskmaster.model.dto.UserInfoDTO;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(getTestUser());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void test_GetInfoWith_Valid_Id() {
        UserInfoDTO info = userService.getInfo(savedUser.getId());
        assertEquals(savedUser.getId(), info.getId());
        assertEquals(savedUser.getUsername(), info.getUsername());
        assertEquals(savedUser.getFullName(), info.getFullName());
        assertEquals(savedUser.getAge(), info.getAge());
        assertEquals(savedUser.getEmail(), info.getEmail());
        assertFalse(info.isAdmin());
    }

    @Test
    void test_GetInfoWith_InValid_Id() {
        assertThrows(UserNotFoundException.class, () -> userService.getInfo(-4L));
    }

    @Test
    public void test_deleteWith_Valid_Id() {
        assertEquals(1, userRepository.count());
        userService.delete(savedUser.getId());
        assertEquals(0, userRepository.count());
    }

    @Test
    public void test_deleteWith_InValid_Id() {
        assertThrows(UserNotFoundException.class, () -> userService.delete(8L));
    }


    @Test
    public void test_Exist() {
        assertTrue(userService.exists(savedUser.getId()));
        assertFalse(userService.exists(7L));
    }

    @Test
    public void test_getAll_With_Empty_SearchQuery() {
        addTestData();
        Page<UserInfoDTO> all = userService.getAll("", getPageable());
        assertEquals(5, all.getTotalElements());
    }

    @Test
    public void test_getAll_With_NotEmpty_SearchQuery() {
        addTestData();
        assertEquals(1, userService.getAll("iv@com.me", getPageable()).getTotalElements());
        assertEquals(1, userService.getAll("Shenol", getPageable()).getTotalElements());
        assertEquals(1, userService.getAll("Nikolay Nikolaev", getPageable()).getTotalElements());
        assertEquals(2, userService.getAll("24", getPageable()).getTotalElements());
    }

    private Pageable getPageable() {
        return PageRequest.of(0, 10);
    }

    private void addTestData() {
        userRepository.save(getTestUser().setUsername("Shenol").setEmail("sh@com.me"));
        userRepository.save(getTestUser().setUsername("Ivan").setEmail("iv@com.me"));
        userRepository.save(getTestUser().setUsername("Georgi").setAge(24).setEmail("ge@com.me"));
        userRepository.save(getTestUser().setUsername("Nikolay").setFullName("Nikolay Nikolaev").
                setAge(24).setEmail("ni@com.me"));
    }

    private static User getTestUser() {
        return new User("test", "Testov", "test@", 20, "pass",
                Set.of(), Set.of(), Set.of(), Set.of(),
                null);
    }
}