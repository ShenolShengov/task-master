package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.Role;
import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    public static final long ID = 1L;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserHelperService userHelperService;

    private AuthorizationServiceImpl authorizationService;
    @BeforeEach
    void setUp() {
        this.authorizationService = new AuthorizationServiceImpl(roleRepository, userRepository, userHelperService);
        when(roleRepository.getByName(UserRoles.ADMIN))
                .thenReturn(new Role(UserRoles.ADMIN));
    }

    @Test
    public void test_MakeAdmin() {
        User testUser = getTestUserWithoutAdminRole();
        when(userHelperService.getUser(ID))
                .thenReturn(testUser);
        assertTrue(testUser.getRoles().stream().noneMatch(e -> e.getName().equals(UserRoles.ADMIN)));
        authorizationService.makeAdmin(ID);
        assertTrue(testUser.getRoles().stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }

    @Test
    public void test_RemoveAdmin() {
        User testUser = getTestUserWithAdminRole();
        when(userHelperService.getUser(ID))
                .thenReturn(testUser);
        assertTrue(testUser.getRoles().stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN)));
        authorizationService.removeAdmin(ID);
        assertTrue(testUser.getRoles().stream().noneMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }

    private User getTestUserWithAdminRole() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(UserRoles.ADMIN));
        roles.add(new Role(UserRoles.USER));
        return new User("Test", "Test Testov", "test@gmail.com",
                20, "pass", roles, Set.of(), Set.of(), Set.of(), null);
    }

    private User getTestUserWithoutAdminRole() {
        User user = getTestUserWithAdminRole();
        user.getRoles().removeIf(e -> e.getName().equals(UserRoles.ADMIN));
        return user;
    }
}