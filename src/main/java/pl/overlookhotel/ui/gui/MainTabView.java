package pl.overlookhotel.ui.gui;

import javafx.scene.control.TabPane;

public class MainTabView {

    private TabPane mainTabs;

    public MainTabView() {
        this.mainTabs = new TabPane();

        RoomsTab roomsTab = new RoomsTab();
        GuestsTab guestsTab = new GuestsTab();
        ReservationsTab reservationsTab = new ReservationsTab();

        this.mainTabs.getTabs().addAll(reservationsTab.getReservationTab(), guestsTab.getGuestTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
