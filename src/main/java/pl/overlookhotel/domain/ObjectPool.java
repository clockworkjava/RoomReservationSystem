package pl.overlookhotel.domain;

import pl.overlookhotel.domain.guest.GuestDatabaseRepository;
import pl.overlookhotel.domain.guest.GuestJpaRepository;
import pl.overlookhotel.domain.guest.GuestRepository;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.reservation.ReservationDatabaseRepository;
import pl.overlookhotel.domain.reservation.ReservationJpaRepository;
import pl.overlookhotel.domain.reservation.ReservationRepository;
import pl.overlookhotel.domain.reservation.ReservationService;
import pl.overlookhotel.domain.room.RoomDatabaseRepository;
import pl.overlookhotel.domain.room.RoomJpaRepository;
import pl.overlookhotel.domain.room.RoomRepository;
import pl.overlookhotel.domain.room.RoomService;

public class ObjectPool {

    private static final RoomService roomService = new RoomService();
    private static final ReservationService reservationService = new ReservationService();


    private ObjectPool() {

    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {

        return GuestJpaRepository.getInstance();
    }

    public static RoomService getRoomService() {

        return roomService;
    }

    public static RoomRepository getRoomRepository() {

        return RoomJpaRepository.getInstance();
    }

    public static ReservationService getReservationService() {

        return reservationService;
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationJpaRepository.getInstance();
    }
}
