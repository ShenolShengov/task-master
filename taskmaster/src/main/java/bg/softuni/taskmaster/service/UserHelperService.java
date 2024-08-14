package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserHelperService {

    boolean isAuthenticated();

    String getUsername();

    String getEmail();

    String getProfilePictureUrl();

    UserDetails toUserDetails(User user);

    boolean isAdmin();

    boolean isAdmin(Long id);

    User getLoggedUser();

    User getUser(Long id);

    String[] getAdminsEmails();
}
