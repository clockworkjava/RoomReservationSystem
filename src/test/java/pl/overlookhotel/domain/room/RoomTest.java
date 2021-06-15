package pl.overlookhotel.domain.room;

import org.junit.jupiter.api.Test;
import pl.overlookhotel.domain.room.dto.RoomDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoomTest {

    @Test
    public void toCSVTest() {

        //given
        List<BedType> bedTypes = Arrays.asList(BedType.values());
        Room room = new Room(1, 666, bedTypes);

        //when
        String csvTemplate = "1,666,Pojedyńcze#Podwójne#Królewskie" + System.getProperty("line.separator");
        String createdCSV = room.toCSV();

        //then
        assertEquals(csvTemplate, createdCSV);
    }

    @Test
    public void toCSVWithNullBedListTest() {

        //given
        Room room = new Room(1, 666, null);

        //when
        String createdCSV = room.toCSV();

        //then
        assertNotNull(room.getBeds());

        String csvTemplate = "1,666," + System.getProperty("line.separator");
        assertEquals(csvTemplate, createdCSV, "Porównanie wygenerowanych formatów CSV przy liście łóżek");
    }

    @Test
    public void toDTOTest() {

        //given
        List<BedType> bedTypes = Arrays.asList(BedType.values());
        Room room = new Room(1, 666, bedTypes);

        //when
        RoomDTO roomDTO = room.generateDTO();

        //then
        assertEquals(roomDTO.getId(), 1);
        assertEquals(roomDTO.getNumber(), 666);
        assertEquals(roomDTO.getBedsCount(), 3);
        assertEquals(roomDTO.getBeds(), "Pojedyńcze,Podwójne,Królewskie");
    }

    @Test
    public void toDTOFromRoomWithNullBedsListTest() {

        //given
        Room room = new Room(1, 666, null);

        //when
        RoomDTO roomDTO = room.generateDTO();

        //then
        assertEquals(roomDTO.getId(), 1);
        assertEquals(roomDTO.getNumber(), 666);
        assertEquals(roomDTO.getBedsCount(), 0);
        assertEquals(roomDTO.getBeds(), "");
    }


}