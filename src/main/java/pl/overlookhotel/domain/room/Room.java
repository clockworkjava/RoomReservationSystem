package pl.overlookhotel.domain.room;

import pl.overlookhotel.domain.room.dto.RoomDTO;

public class Room {

    private final int id;
    private final int number;
    private final BedType[] beds;

    Room(int id, int number, BedType[] bedTypes) {
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

        String[] bedsAsString = getBedsAsString();

        String bedTypes = String.join("#", bedsAsString);

        return String.format("%d,%d,%s%s", this.id, this.number, bedTypes, System.getProperty("line.separator"));
    }

    private String[] getBedsAsString() {
        String[] bedsAsString = new String[this.beds.length];

        for (int i = 0; i < this.beds.length; i++) {
            bedsAsString[i] = this.beds[i].toString();
        }
        return bedsAsString;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public RoomDTO generateDTO() {
        String[] bedsAsString = getBedsAsString();

        String bedTypes = String.join(",", bedsAsString);

    return new RoomDTO(this.id, this.number, bedTypes);
    }

}
