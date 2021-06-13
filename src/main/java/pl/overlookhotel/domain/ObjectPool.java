package pl.overlookhotel.domain;

import pl.overlookhotel.domain.guest.GuestDatabaseRepository;
import pl.overlookhotel.domain.guest.GuestRepository;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.reservation.ReservationDatabaseRepository;
import pl.overlookhotel.domain.reservation.ReservationFileRepository;
import pl.overlookhotel.domain.reservation.ReservationRepository;
import pl.overlookhotel.domain.reservation.ReservationService;
import pl.overlookhotel.domain.room.RoomDatabaseRepository;
import pl.overlookhotel.domain.room.RoomRepository;
import pl.overlookhotel.domain.room.RoomService;

public class ObjectPool {

    private ObjectPool() {

    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }
    public static GuestRepository getGuestRepository() {
        return GuestDatabaseRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return RoomService.getInstance();
    }

    public static RoomRepository getRoomRepository() {
//        return RoomFileRepository.getInstance();
        return RoomDatabaseRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return ReservationService.getInstance();
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationDatabaseRepository.getInstance();
    }
}
