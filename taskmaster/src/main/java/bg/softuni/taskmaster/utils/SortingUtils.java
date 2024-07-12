package bg.softuni.taskmaster.utils;

import bg.softuni.taskmaster.model.anottation.SortParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
public class SortingUtils {

    public static List<String> getSortingFields(String clazz) throws ClassNotFoundException {
        return Arrays.stream(Class.
                        forName("bg.softuni.taskmaster.model.entity." + clazz)
                        .getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(SortParam.class))
                .map(Field::getName)
                .toList();
    }

    public String getSortingFormattedSortingFiled(String field) {
        return StringUtils.capitalize(field.replaceAll("(.)([A-Z])", "$1 $2"));
    }

    public static void addSelectedSortOptions(Model model, String sort, String prefix) {
        String[] sortTokens = sort.split(",");
        model.addAttribute(prefix + "sortProperties", sortTokens[0]);
        model.addAttribute(prefix + "sortDirection", sortTokens[1]);
    }

    public static Pageable checkForDefaultSorting(String sort, Pageable pageable) {
        if (sort.startsWith(",")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.Direction.fromString(sort.substring(1)), "id");
        }
        return pageable;
    }

    public static void addSelectedSortOptions(Model model, String sort) {
        addSelectedSortOptions(model, sort, "");
    }
}
