package bg.sofuni.mailsender.dto.enums;

import lombok.Getter;

@Getter
public enum EmailTemplate {

    USER_REGISTRATION("user-registration"),
    USER_REGISTRATION_ALTER("user_registration_alter"),
    CONTACT_US("contact-us"),
    DELETE_ACCOUNT("delete-account"),
    CHANGE_PASSWORD("change-password"),
    ANSWER_TO_QUESTION("answer-to-question");

    private final String emailTemplateName;

    EmailTemplate(String emailTemplateName) {
        this.emailTemplateName = emailTemplateName;
    }
}
