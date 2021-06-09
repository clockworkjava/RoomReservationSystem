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
import pl.overlookhotel.domain.reservation.ReservationService;
import pl.overlookhotel.domain.reservation.dto.ReservationDTO;

import java.time.LocalDateTime;

public class ReservationsTab {

    private Tab reservationTab;
    private ReservationService reservationService = ObjectPool.getReservationService();

    public ReservationsTab(Stage primaryStage){
        TableView<ReservationDTO> tableView = getReservationDTOTableView();

        Button button = new Button("Dodaj nową rezerwację");

        button.setOnAction(actionEvent -> {
            Stage stg = new Stage();
            stg.initModality(Modality.WINDOW_MODAL);
            stg.initOwner(primaryStage);
            stg.setScene(new AddNewReservationScene(stg, tableView).getMainScene());
            stg.setTitle("Utwórz rezerwację");

            stg.showAndWait();
        });

        VBox layout = new VBox(button, tableView);

        this.reservationTab = new Tab("Rezerwacje", layout);
        this.reservationTab.setClosable(false);

    }

    private TableView<ReservationDTO> getReservationDTOTableView() {
        TableView<ReservationDTO> tableView = new TableView<>();

        TableColumn<ReservationDTO, LocalDateTime> fromColumn = new TableColumn<>("Od");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<ReservationDTO, LocalDateTime> toColumn = new TableColumn<>("Do");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));

        TableColumn<ReservationDTO, Integer> roomColumn = new TableColumn<>("Pokój");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<ReservationDTO, Integer> guestColumn = new TableColumn<>("Rezerwujący");
        guestColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        tableView.getColumns().addAll(fromColumn,toColumn,roomColumn,guestColumn);

        tableView.getItems().addAll(reservationService.getAllReservationsAsDTO());
        return tableView;
    }

    public Tab getReservationTab() {
        return reservationTab;
    }
}
