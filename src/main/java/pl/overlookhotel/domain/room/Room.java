package pl.overlookhotel.domain.room;

import pl.overlookhotel.domain.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final long id;
    private  int number;
    private  List<BedType> beds;

    Room(long id, int number, List<BedType> bedTypes) {
        this.id = id;
        this.number = number;
        this.beds = bedTypes;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Rodzaj łóżek w pokoju:\n");
        for (BedType bed : beds) {
            bedInfo.append("\t ").append(bed.getDescription()).append("\n");
        }

        return String.format("%d Numer: %d %s",this.id, this.number, bedInfo);
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
}
