package bg.softuni.taskmaster.utils;


import bg.softuni.taskmaster.model.enums.EmailParam;

import java.util.EnumMap;
import java.util.stream.IntStream;

public class EmailUtils {

    public static final String APP_MAIL = "taskmastersoftuni@gmail.com";

    public static final String SUCCESSFULLY_SEND_EMAIL_MESSAGE = "Successfully sent email";
    public static final String APP_NAME = "TaskMaster";
    public static final String REGISTRATION_SUBJECT = "Welcome to " + APP_NAME + ", %s!";
    public static final String REGISTRATION_ALTER_SUBJECT = "[Alert] - New registration";

    public static final String CONTACT_US_SUBJECT = "[Contact us] - %s";
    public static final String ACCOUNT_DELETION_SUBJECT = "Account Deletion: %s";
    public static final String CHANGE_PASSWORD_SUBJECT = "Your Password Has Been Successfully Changed";
    public static final String ANSWER_TO_QUESTION_SUBJECT = "New answer to question - %s";

    public static final String LINK_TO_QUESTION = "http://localhost:8080/questions/%s";

    public static EnumMap<EmailParam, String> toParams(Object... params) {
        return IntStream.iterate(0, e -> e + 1 < params.length, e -> e + 2)
                .collect(
                        () -> new EnumMap<>(EmailParam.class),
                        (k, m) -> k.put((EmailParam) params[m], (String) params[m + 1]),
                        EnumMap::putAll
                );
    }
}
