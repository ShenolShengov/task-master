package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.entity.User;
import bg.softuni.taskmaster.model.enums.UserRoles;
import bg.softuni.taskmaster.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserTestUtils {

    public final String TEST_FULL_NAME = "Test full name";
    private final UserRepository userRepository;

    private final PictureTestUtils pictureTestUtils;

    private final RoleTestUtils roleTestUtils;

    public UserTestUtils(UserRepository userRepository, PictureTestUtils pictureTestUtils, RoleTestUtils roleTestUtils) {
        this.userRepository = userRepository;
        this.pictureTestUtils = pictureTestUtils;
        this.roleTestUtils = roleTestUtils;
    }

    public static User getTestUser(String username, String email, boolean isAdmin) {
        User user = new User(username, "Test full name", email, 20, "password",
                new HashSet<>(List.of(RoleTestUtils.getTestRole(UserRoles.USER))),
                Set.of(), Set.of(), Set.of(),
                null);
        if (isAdmin) {
            user.getRoles().add(RoleTestUtils.getTestRole(UserRoles.ADMIN));
        }
        return user;
    }


    public User getOrSaveTestUserFromDB(String username, String email, String fullName, Integer age, boolean isAdmin) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseGet(() -> {
                    User user = new User(username, fullName, email, age, "password",
                            new HashSet<>(List.of(roleTestUtils.getRole(UserRoles.USER))),
                            new HashSet<>(), new HashSet<>(), new HashSet<>(),
                            pictureTestUtils.saveOrGetPicture());
                    if (isAdmin) {
                        user.getRoles().add(roleTestUtils.getRole(UserRoles.ADMIN));
                    }
                    return userRepository.save(user);
                }
        );
    }

    public User getOrSaveTestUserFromDB(String username, String email, boolean isAdmin) {
        return getOrSaveTestUserFromDB(username, email, TEST_FULL_NAME, 20, isAdmin);
    }

    public User getOrSaveTestUserFromDB(String username, String email, String fullName) {
        return getOrSaveTestUserFromDB(username, email, fullName, 20, false);
    }

    public User getOrSaveTestUserFromDB(String username, String email, Integer age) {
        return getOrSaveTestUserFromDB(username, email, TEST_FULL_NAME, age, false);
    }

    public User getOrSaveTestUserFromDB(String username, String email) {
        return getOrSaveTestUserFromDB(username, email, false);
    }


    public void clearDB() {
        userRepository.deleteAll();
    }
}
