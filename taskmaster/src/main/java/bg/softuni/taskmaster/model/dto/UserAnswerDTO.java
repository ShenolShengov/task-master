package bg.softuni.taskmaster.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserAnswerDTO {

    private String username;
    private String profilePictureUrl;
}
