package bg.softuni.taskmaster.service.converters;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringToInt implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        if (Arrays.stream(source.split(" "))
                .map(e -> e.charAt(0)).allMatch(Character::isDigit)){
            return Integer.parseInt(source);
        }
        return  null;
    }
}
