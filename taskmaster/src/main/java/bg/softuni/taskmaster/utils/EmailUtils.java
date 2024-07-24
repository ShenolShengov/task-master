package bg.softuni.taskmaster.utils;


import bg.softuni.taskmaster.model.enums.EmailParam;

import java.util.EnumMap;
import java.util.stream.IntStream;

public class EmailUtils {

    public static final String APP_MAIL = "taskmastersoftuni@gmail.com";
    public static final String APP_NAME = "TaskMaster";
    public static final String REGISTRATION_SUBJECT = "Welcome to " + APP_NAME + ", %s!";

    public static EnumMap<EmailParam, String> toParams(Object... params) {
        EnumMap<EmailParam, String> map = new EnumMap<>(EmailParam.class);
//        for (int i = 0; i < params.length; i += 2) {
//            map.put((EmailParam) params[i], (String) params[i + 1]);
//        }
//        return map;
        return IntStream.iterate(0, e -> e + 1 < params.length, e -> e + 2)
                .collect(
                        () -> new EnumMap<>(EmailParam.class),
                        (k, m) -> k.put((EmailParam) params[m], (String) params[m + 1]),
                        EnumMap::putAll
                );
    }
}
