package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.User;

import java.util.List;

public interface UserHelperService {

    boolean isAuthenticated();

    String getUsername();

    String getEmail();

    boolean isAdmin();

    boolean isAdmin(Long id);

    User getLoggedUser();

    User getUser(Long id);

    String[] getAdminsEmails();
}
