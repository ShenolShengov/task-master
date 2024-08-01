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

    public static final String TEST_FULL_NAME = "Test full name";
    private static UserRepository userRepository;

    public static User getTestUser(String username, String email, boolean isAdmin) {
        User user = new User(username, "Test full name", email, 20, "password",
                new HashSet<>(List.of(RoleTestUtils.getRole(UserRoles.USER, false))),
                Set.of(), Set.of(), Set.of(),
                PictureTestUtils.getPicture(false));
        if (isAdmin) {
            user.getRoles().add(RoleTestUtils.getRole(UserRoles.ADMIN, false));
        }
        return user;
    }


    public static User getOrSaveTestUserFromDB(String username, String email, String fullName, Integer age, boolean isAdmin) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseGet(() -> {
                    User user = new User(username, fullName, email, age, "password",
                            new HashSet<>(List.of(RoleTestUtils.getRole(UserRoles.USER, true))),
                            new HashSet<>(), new HashSet<>(), new HashSet<>(),
                            PictureTestUtils.getPicture(true));
                    if (isAdmin) {
                        user.getRoles().add(RoleTestUtils.getRole(UserRoles.ADMIN, true));
                    }
                    return userRepository.save(user);
                }
        );
    }

    public static User getOrSaveTestUserFromDB(String username, String email, boolean isAdmin) {
        return getOrSaveTestUserFromDB(username, email, TEST_FULL_NAME, 20, isAdmin);
    }

    public static User getOrSaveTestUserFromDB(String username, String email, String fullName) {
        return getOrSaveTestUserFromDB(username, email, fullName, 20, false);
    }

    public static User getOrSaveTestUserFromDB(String username, String email, Integer age) {
        return getOrSaveTestUserFromDB(username, email, TEST_FULL_NAME, age, false);
    }

    public static User getOrSaveTestUserFromDB(String username, String email) {
        return getOrSaveTestUserFromDB(username, email, false);
    }


    public static void setUserRepository(UserRepository userRepository) {
        UserTestUtils.userRepository = userRepository;
    }

    public static void clearDB() {
        userRepository.deleteAll();
    }
}
