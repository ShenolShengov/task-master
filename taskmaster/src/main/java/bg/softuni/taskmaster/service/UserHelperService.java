package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.User;

public interface UserHelperService {

    boolean isAuthenticated();

    String getUsername();

    boolean isAdmin();

    boolean isAdmin(Long id);

    User getLoggedUser();

    User getUser(Long id);
}
