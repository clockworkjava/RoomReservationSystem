package pl.overlookhotel.ui.gui;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import pl.overlookhotel.ui.gui.guests.GuestsTab;
import pl.overlookhotel.ui.gui.reservations.ReservationsTab;
import pl.overlookhotel.ui.gui.rooms.RoomsTab;

public class MainTabView {

    private TabPane mainTabs;

    public MainTabView(Stage primaryStage) {
        this.mainTabs = new TabPane();

        RoomsTab roomsTab = new RoomsTab(primaryStage);
        GuestsTab guestsTab = new GuestsTab(primaryStage);
        ReservationsTab reservationsTab = new ReservationsTab(primaryStage);

        this.mainTabs.getTabs().addAll(reservationsTab.getReservationTab(), guestsTab.getGuestTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
