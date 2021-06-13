package pl.overlookhotel.domain.reservation;

import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.guest.Guest;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.reservation.dto.ReservationDTO;
import pl.overlookhotel.domain.room.Room;
import pl.overlookhotel.domain.room.RoomService;
import pl.overlookhotel.util.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private final RoomService roomService = ObjectPool.getRoomService();
    private final GuestService guestService = ObjectPool.getGuestService();
    private final ReservationRepository repository = ObjectPool.getReservationRepository();

    private static final ReservationService instance = new ReservationService();

    private ReservationService(){

    }

    public static ReservationService getInstance() {
        return instance;
    }

    public Reservation createNewReservation(LocalDate from, LocalDate to, int roomId, int guestId) throws IllegalArgumentException {

        Room room = this.roomService.getRoomById(roomId);
        Guest guest = this.guestService.getGuestById(guestId);

        LocalDateTime fromWithTime = from.atTime(SystemUtils.HOTEL_NIGHT_START_HOUR, SystemUtils.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithTime = to.atTime(SystemUtils.HOTEL_NIGHT_END_HOUR, SystemUtils.HOTEL_NIGHT_END_MINUTE);

        if (toWithTime.isBefore(fromWithTime)) {
            throw new IllegalArgumentException();
        }

        return this.repository.createNewReservation(room, guest, fromWithTime, toWithTime);
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void saveAll() {
        this.repository.saveAll();

    }

    public void removeReservation(int id){
        this.repository.remove(id);
    }

    public List<ReservationDTO> getAllReservationsAsDTO(){
        List<ReservationDTO> result = new ArrayList<>();

        List<Reservation> allReservations = this.repository.getAllReservations();

        for (Reservation reservation : allReservations){
            ReservationDTO dto = reservation.getAsDTO();
            result.add(dto);
        }
        return result;
    }
}
