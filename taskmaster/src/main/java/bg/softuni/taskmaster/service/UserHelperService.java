package bg.softuni.taskmaster.service;

import bg.softuni.taskmaster.model.entity.User;
import org.apache.tomcat.websocket.AuthenticationException;

public interface UserHelperService {

    boolean isAuthenticated();

    String getName();

    boolean haseRole(String role);

    User getUser() throws AuthenticationException;
}
