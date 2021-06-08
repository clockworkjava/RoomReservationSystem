package pl.overlookhotel;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.reservation.ReservationService;
import pl.overlookhotel.domain.room.RoomService;
import pl.overlookhotel.exceptions.PersistenceToFileException;
import pl.overlookhotel.ui.gui.PrimaryStage;
import pl.overlookhotel.ui.text.TextUI;
import pl.overlookhotel.util.Properties;

import java.io.IOException;

public class App extends Application {

    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = ObjectPool.getGuestService();
    private static final RoomService roomService = ObjectPool.getRoomService();
    private static final ReservationService reservationService = ObjectPool.getReservationService();


    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();

            System.out.println("Trwa ładowanie danych...");
            guestService.readAll();
            roomService.readAll();
            reservationService.readAll();
            System.out.println("Dane załadowane");
        } catch (IOException e) {
            throw new PersistenceToFileException(Properties.DATA_DIRECTORY.toString(), "create", "directory");
        }
        Application.launch(args);
//        textUI.showSystemInfo();
//        textUI.showMainMenu();
    }

    @Override
    public void start(Stage primaryStage) {
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }

    @Override
    public void stop() {
        System.out.println("Dane zapisane");
        guestService.saveAll();
        roomService.saveAll();
        reservationService.saveAll();
    }
}
