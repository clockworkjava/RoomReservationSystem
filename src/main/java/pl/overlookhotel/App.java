package pl.overlookhotel;

import pl.overlookhotel.ui.text.TextUI;

public class App {

    private static final TextUI textUI = new TextUI();

    static final String HOTEL_NAME = "Overlook";
    static final int SYSTEM_VERSION = 1;
    static final boolean IS_DEVELOPER_VERSION = true;

    public static void main(String[] args) {

        textUI.showSystemInfo(HOTEL_NAME, SYSTEM_VERSION, IS_DEVELOPER_VERSION);
        textUI.showMainMenu();
    }
}
