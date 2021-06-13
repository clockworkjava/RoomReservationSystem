package pl.overlookhotel.domain.room.dto;

public class RoomDTO {

    private long id;
    private int number;
    private String beds;
    private int bedsCount;

    public RoomDTO(long id, int number, String beds, int bedsCount) {
        this.id = id;
        this.number = number;
        this.beds = beds;
        this.bedsCount = bedsCount;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getBeds() {
        return beds;
    }

    public int getBedsCount() {
        return bedsCount;
    }
}
