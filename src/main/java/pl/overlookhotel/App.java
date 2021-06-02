package pl.overlookhotel;

import pl.overlookhotel.exceptions.PersistenceToFileException;
import pl.overlookhotel.ui.text.TextUI;
import pl.overlookhotel.util.Properties;

import java.io.IOException;

public class App {

    private static final TextUI textUI = new TextUI();


    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();
        } catch (IOException e) {
        throw new PersistenceToFileException(Properties.DATA_DIRECTORY.toString(), "create", "directory");
        }
        textUI.showSystemInfo();
        textUI.showMainMenu();
    }
}
