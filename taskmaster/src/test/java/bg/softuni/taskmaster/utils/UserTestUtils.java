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

    private static UserRepository userRepository;

    public static User getMockUser(String username, String email, boolean isAdmin) {
        User user = new User(username, email, "mock@gmail.com", 20, "password",
                new HashSet<>(List.of(RoleTestUtils.getRole(UserRoles.USER, false))),
                Set.of(), Set.of(), Set.of(),
                PictureTestUtils.getDefaultProfilePicture(false));
        if (isAdmin) {
            user.getRoles().add(RoleTestUtils.getRole(UserRoles.ADMIN, false));
        }
        return user;
    }

    public static User getOrSaveMockUserFromDB(String username, String email, boolean isAdmin) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseGet(() -> {
                    User user = new User(username, "Mock mocker", email, 20, "password",
                            new HashSet<>(List.of(RoleTestUtils.getRole(UserRoles.USER, true))),
                            new HashSet<>(), new HashSet<>(), new HashSet<>(),
                            PictureTestUtils.getDefaultProfilePicture(true));
                    if (isAdmin) {
                        user.getRoles().add(RoleTestUtils.getRole(UserRoles.ADMIN, true));
                    }
                    return userRepository.save(user);
                }
        );
    }

    public static User getOrSaveMockUserFromDB(String username, String email) {
        return getOrSaveMockUserFromDB(username, email, false);
    }

    public static void setUserRepository(UserRepository userRepository) {
        UserTestUtils.userRepository = userRepository;
    }

    public static void clearDB() {
        userRepository.deleteAll();
    }
}
