package bg.softuni.taskmaster.validation.uniquefield;

import org.thymeleaf.util.StringUtils;

public enum NotTakenType {
    USERNAME, EMAIL;

    @Override
    public String toString() {
        return StringUtils.capitalize(super.toString().toLowerCase());
    }
}
