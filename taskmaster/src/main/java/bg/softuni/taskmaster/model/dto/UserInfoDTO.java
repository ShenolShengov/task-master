package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserInfoDTO implements Serializable {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Integer age;
    private boolean isAdmin;
}
