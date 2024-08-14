package bg.softuni.taskmaster.model.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class TaskMasterUserDetails extends User {

    private final String profilePictureUrl;

    public TaskMasterUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String profilePictureUrl) {
        super(username, password, authorities);
        this.profilePictureUrl = profilePictureUrl;
    }
}
