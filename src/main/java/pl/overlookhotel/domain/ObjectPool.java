package pl.overlookhotel.domain;

import pl.overlookhotel.domain.guest.GuestRepository;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.reservation.ReservationRepository;
import pl.overlookhotel.domain.reservation.ReservationService;
import pl.overlookhotel.domain.room.RoomRepository;
import pl.overlookhotel.domain.room.RoomService;

public class ObjectPool {

    private ObjectPool() {

    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
        return GuestRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return RoomService.getInstance();
    }

    public static RoomRepository getRoomRepository() {
        return RoomRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return ReservationService.getInstance();
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }
}
