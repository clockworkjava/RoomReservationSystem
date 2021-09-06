package pl.overlookhotel.domain.room;

import javafx.application.Platform;
import pl.overlookhotel.domain.room.dto.RoomDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int number;

    @ElementCollection(targetClass = BedType.class)
    private List<BedType> beds;

    Room(long id, int number, List<BedType> bedTypes) {
        this.id = id;
        this.number = number;
        if (bedTypes == null) {
            this.beds = new ArrayList<>();
        } else {
            this.beds = bedTypes;
        }
    }

    public Room(int number, List<BedType> beds) {
        this.number = number;
        this.beds = beds;
    }

    public Room() {
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Rodzaj łóżek w pokoju:\n");
        for (BedType bed : beds) {
            bedInfo.append("\t ").append(bed.getDescription()).append("\n");
        }

        return String.format("%d Numer: %d %s", this.id, this.number, bedInfo);
    }

    String toCSV() {

        List<String> bedsAsString = getBedsAsString();

        String bedTypes = String.join("#", bedsAsString);

        return String.format("%d,%d,%s%s", this.id, this.number, bedTypes, System.getProperty("line.separator"));
    }

    private List<String> getBedsAsString() {

        List<String> bedsAsString = new ArrayList<>();

        for (int i = 0; i < this.beds.size(); i++) {
            bedsAsString.add(this.beds.get(i).toString());
        }
        return bedsAsString;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public RoomDTO generateDTO() {

        List<String> bedsAsString = getBedsAsString();

        String bedTypes = String.join(",", bedsAsString);

        return new RoomDTO(this.id, this.number, bedTypes, beds.size());
    }

    void addBed(BedType bedType) {
        this.beds.add(bedType);
    }

    void setNumber(int number) {
        this.number = number;
    }

    public void setBeds(List<BedType> bedTypes) {
        this.beds = bedTypes;
    }

    public List<BedType> getBeds() {
        return this.beds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && number == room.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
