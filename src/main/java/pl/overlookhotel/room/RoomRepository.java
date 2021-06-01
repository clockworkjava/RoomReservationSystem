package pl.overlookhotel.room;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int number, BedType[] bedTypes) {
        Room newRoom = new Room(number, bedTypes);
        rooms.add(newRoom);

        return newRoom;
    }

    public List<Room> getAllRooms() {
        return this.rooms;
    }
}
