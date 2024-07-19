package bg.softuni.taskmaster.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserAnswerDTO implements Serializable {

    private String username;
    private String profilePictureUrl;
}
