package pl.overlookhotel.guest;

public class GuestService {

    private final GuestRepository repository = new GuestRepository();

    public Guest createNewGuest(String firstName, String lastName, int age, boolean isMale) {

        Gender gender = Gender.FEMALE;

        if (isMale) {
            gender = Gender.MALE;
        }
        return repository.createNewGuest(firstName, lastName, age, gender);
    }
}