package pl.overlookhotel.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.guest.dto.GuestDTO;

public class GuestsTab {

    private Tab guestTab;
    private GuestService guestService = ObjectPool.getGuestService();

    public GuestsTab(Stage primaryStage) {
        TableView<GuestDTO> tableView = getGuestDTOTableView();

        Button button = new Button("Stwórz nowego gościa");

        button.setOnAction(actionEvent -> {
            Stage stg = new Stage();
            stg.initModality(Modality.WINDOW_MODAL);
            stg.initOwner(primaryStage);
            stg.setScene(new AddNewGuestScene(stg, tableView).getMainScene());
            stg.setTitle("Dodawanie nowego pokoju");

            stg.showAndWait();
        });

        VBox layout = new VBox(button, tableView);

        this.guestTab = new Tab("Goście", layout);
        this.guestTab.setClosable(false);

    }

    private TableView<GuestDTO> getGuestDTOTableView() {
        TableView<GuestDTO> tableView = new TableView<>();
        TableColumn<GuestDTO, String> firstNameColumn = new TableColumn<>("Imię");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<GuestDTO, String> lastNameColumn = new TableColumn<>("Nazwisko");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<GuestDTO, Integer> ageColumn = new TableColumn<>("wiek");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<GuestDTO, String> genderColumn = new TableColumn<>("płeć");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, ageColumn, genderColumn);

        tableView.getItems().addAll(guestService.getAllGuestsAsDTO());
        return tableView;
    }

    public Tab getGuestTab() {
        return guestTab;
    }
}
