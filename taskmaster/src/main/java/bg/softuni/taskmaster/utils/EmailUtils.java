package bg.softuni.taskmaster.utils;


import bg.softuni.taskmaster.model.enums.EmailParam;

import java.util.EnumMap;
import java.util.stream.IntStream;

public class EmailUtils {

    public static final String APP_MAIL = "taskmastersoftuni@gmail.com";

    public static final String ADMIN_MAIL = "shenolshengov41@gmail.com";
    public static final String APP_NAME = "TaskMaster";
    public static final String REGISTRATION_SUBJECT = "Welcome to " + APP_NAME + ", %s!";
    public static final String REGISTRATION_ALTER_SUBJECT = "[Alter] - New registration";
    public static final String ACCOUNT_DELETION_SUBJECT = "Account Deletion: %s";

    public static EnumMap<EmailParam, String> toParams(Object... params) {
        return IntStream.iterate(0, e -> e + 1 < params.length, e -> e + 2)
                .collect(
                        () -> new EnumMap<>(EmailParam.class),
                        (k, m) -> k.put((EmailParam) params[m], (String) params[m + 1]),
                        EnumMap::putAll
                );
    }
}
