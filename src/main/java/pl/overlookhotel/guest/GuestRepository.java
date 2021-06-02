package pl.overlookhotel.guest;

import pl.overlookhotel.exceptions.PersistenceToFileException;
import pl.overlookhotel.util.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private final List<Guest> guests = new ArrayList<>();

    Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest newGuest = new Guest(firstName, lastName, age, gender);
        guests.add(newGuest);
        return newGuest;
    }

    public List<Guest> getAll() {
        return this.guests;
    }

    void saveAll() {
        String name = "guests.csv";

        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        StringBuilder sb = new StringBuilder("");

        for (Guest guest : this.guests) {
            sb.append(guest.toCSV());
        }

        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
        throw new PersistenceToFileException(file.toString(), "write", "guest data");
        }
    }

    void readAll(){
        String name = "guests.csv";

        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] guestsAsString = data.split(System.getProperty("line.separator"));

            for (String guestAsString : guestsAsString){
                String[] guestData = guestAsString.split(",");
                String firstName = guestData[0];
                String lastName = guestData[1];
                int age = Integer.parseInt(guestData[2]);
                Gender gender = Gender.valueOf(guestData[3]);
                createNewGuest(firstName, lastName, age, gender);
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "guests data");
        }
    }

}
