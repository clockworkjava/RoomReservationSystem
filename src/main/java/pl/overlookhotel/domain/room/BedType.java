package pl.overlookhotel.domain.room;

public enum BedType {
    SINGLE("Pojedyńcze"),
    DOUBLE("Podwójne"),
    KING_SIZE("Królewskie");

    private final String description;

    BedType(String description) {
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
