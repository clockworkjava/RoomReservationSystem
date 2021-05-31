package pl.overlookhotel.room;

public class Room {

    private final int number;
    private final BedType[] beds;

    Room(int number, BedType[] beds) {
        this.number = number;
        this.beds = beds;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Rodzaj łóżek w pokoju:\n");
        for (BedType bed : beds) {
            bedInfo.append("\t ").append(bed.getDescription()).append("\n");
        }

        return String.format("Dodano nowy pokój nr %d %s", this.number, bedInfo);
    }
}
