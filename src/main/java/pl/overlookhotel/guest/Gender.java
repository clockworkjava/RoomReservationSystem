package pl.overlookhotel.guest;

public enum Gender {
    FEMALE("Kobieta"),
    MALE("Mężczyzna");

    private final String description;

    Gender (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
