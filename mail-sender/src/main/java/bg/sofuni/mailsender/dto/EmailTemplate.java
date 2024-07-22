package bg.sofuni.mailsender.dto;

import lombok.Getter;

@Getter
public enum EmailTemplate {

    REGISTRATION_USER("registration-user-email"),
    REGISTRATION_ADMIN("registration-admin-email"),
    CONTACT_US("contact-us-email");

    private final String emailTemplateName;

    EmailTemplate(String emailTemplateName) {
        this.emailTemplateName = emailTemplateName;
    }
}
