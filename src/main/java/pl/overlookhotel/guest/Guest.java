package pl.overlookhotel.guest;

public class Guest {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Gender gender;

    Guest(int id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public String getInfo() {
        return String.format("%d %s %s (%d) (%s) ",
                this.id,
                this.firstName,
                this.lastName,
                this.age,
                this.gender.getDescription());
    }

    String toCSV() {
        return String.format("%s,%s,%s,%d,%s%s",
                this.id,
                this.firstName,
                this.lastName,
                this.age,
                this.gender,
                System.getProperty("line.separator"));
    }

    public int getId() {
        return id;
    }
}
