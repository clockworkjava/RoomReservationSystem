package pl.overlookhotel.domain.room;

import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.room.dto.RoomDTO;
import pl.overlookhotel.exceptions.WrongOptionException;
import pl.overlookhotel.util.Properties;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final RoomRepository repository = ObjectPool.getRoomRepository();

    private final static RoomService instance = new RoomService();

    private RoomService() {

    }

    public static RoomService getInstance() {
        return instance;
    }

    public Room createNewRoom(int number, List<String> bedTypesAsString) {
        BedType[] bedTypes = new BedType[bedTypesAsString.size()];

        for (int i = 0; i < bedTypesAsString.size(); i++) {

            BedType bedType;

            if (bedTypesAsString.get(i).equals(Properties.SINGLE_BED)) {
                bedType = BedType.SINGLE;
            } else if (bedTypesAsString.get(i).equals(Properties.DOUBLE_BED)) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesAsString.get(i).equals(Properties.KING_SIZE_BED)) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in bed type selection");
            }

            bedTypes[i] = bedType;
        }

        return repository.createNewRoom(number, bedTypes);
    }

    public Room createNewRoom(int number, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i = 0; i < bedTypesOptions.length; i++) {

            BedType bedType;

            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in bed type selection");
            }

            bedTypes[i] = bedType;
        }

        return repository.createNewRoom(number, bedTypes);
    }

    public List<Room> getAllRooms() {
        return this.repository.getAllRooms();
    }

    public void saveAll() {
        this.repository.saveAll();
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void removeRoom(int id) {
        this.repository.remove(id);

    }


    public void editRoom(int id, int number, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i = 0; i < bedTypesOptions.length; i++) {

            BedType bedType;

            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in bed type selection");
            }

            bedTypes[i] = bedType;
        }

        this.repository.edit(id, number, bedTypes);

    }

    public Room getRoomById(int roomId) {
        return this.repository.getById(roomId);

    }

    public List<RoomDTO> getAllAsDTO() {
        List<RoomDTO> result = new ArrayList<>();

        List<Room> allRooms = repository.getAllRooms();

        for (Room room : allRooms) {
            RoomDTO dto = room.generateDTO();
            result.add(dto);
        }
        return result;
    }
}
