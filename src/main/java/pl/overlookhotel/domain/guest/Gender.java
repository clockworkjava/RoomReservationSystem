package pl.overlookhotel.domain.guest;

import pl.overlookhotel.util.SystemUtils;

public enum Gender {
    FEMALE(SystemUtils.FEMALE),
    MALE(SystemUtils.MALE);

    private final String description;

    Gender (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
