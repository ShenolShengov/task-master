package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.RoleRepository;
import bg.softuni.taskmaster.repository.UserRepository;
import bg.softuni.taskmaster.service.UserHelperService;
import bg.softuni.taskmaster.utils.RoleTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static bg.softuni.taskmaster.utils.UserTestUtils.getTestUser;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    public static final long ID = 1L;

    private AuthorizationServiceImpl authorizationServiceToTest;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserHelperService mockUserHelperService;

    @BeforeEach
    void setUp() {
        this.authorizationServiceToTest = new AuthorizationServiceImpl(mockRoleRepository,
                mockUserRepository, mockUserHelperService);
        when(mockRoleRepository.getByName(UserRoles.ADMIN))
                .thenReturn(RoleTestUtils.getRole(UserRoles.ADMIN, false));
    }

    @Test
    public void test_MakeAdmin() {
        User testUser = getTestUser("Test", "test@com.me", false);
        when(mockUserHelperService.getUser(ID)).thenReturn(testUser);

        assertTrue(testUser.getRoles().stream().noneMatch(e -> e.getName().equals(UserRoles.ADMIN)));
        authorizationServiceToTest.makeAdmin(ID);
        assertTrue(testUser.getRoles().stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }

    @Test
    public void test_RemoveAdmin() {
        User testUser = getTestUser("Test", "test@com.me", true);
        when(mockUserHelperService.getUser(ID)).thenReturn(testUser);

        assertTrue(testUser.getRoles().stream().anyMatch(e -> e.getName().equals(UserRoles.ADMIN)));
        authorizationServiceToTest.removeAdmin(ID);
        assertTrue(testUser.getRoles().stream().noneMatch(e -> e.getName().equals(UserRoles.ADMIN)));
    }
}