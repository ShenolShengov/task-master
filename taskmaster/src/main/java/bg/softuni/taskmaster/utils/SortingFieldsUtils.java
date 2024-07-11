package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.anottation.SortParam;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
public class SortingFieldsUtils {

    public List<String> getSortingFields(String clazz) throws ClassNotFoundException {
        return Arrays.stream(Class.
                        forName("bg.softuni.taskmaster.model.entity." + clazz)
                        .getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(SortParam.class))
                .map(Field::getName)
                .toList();

    }
}
