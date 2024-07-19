package bg.softuni.taskmaster.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Integer age;
}
