package pl.overlookhotel.domain.reservation;

import pl.overlookhotel.domain.guest.Guest;
import pl.overlookhotel.domain.room.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to);

    void saveAll();

    void readAll();

    List<Reservation> getAllReservations();

    void remove(long id);
}
