package pl.overlookhotel.ui.gui.rooms;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.room.RoomService;
import pl.overlookhotel.domain.room.dto.RoomDTO;

import java.util.List;

public class RoomsTab {

    private Tab roomTab;
    private RoomService roomService = ObjectPool.getRoomService();
    private VBox layout;
    private Stage primaryStage;

    public RoomsTab(Stage primaryStage) {
        TableView<RoomDTO> tableView = getRoomDTOTableView();
        this.primaryStage = primaryStage;
        Button button = new Button("Stwórz nowy pokój");

        button.setOnAction(actionEvent -> {
            Stage stg = new Stage();
            stg.initModality(Modality.WINDOW_MODAL);
            stg.initOwner(this.primaryStage);
            stg.setScene(new AddNewRoomScene(stg, tableView).getMainScene());
            stg.setTitle("Dodaj nowy pokój");

            stg.showAndWait();
        });

        VBox layout = new VBox(button, tableView);

        this.roomTab = new Tab("Pokoje", layout);
        this.roomTab.setClosable(false);
    }

    private TableView<RoomDTO> getRoomDTOTableView() {
        TableView<RoomDTO> tableView = new TableView<>();

        TableColumn<RoomDTO, Integer> numberColumn = new TableColumn<>("Numer");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<RoomDTO, String> bedsColumn = new TableColumn<>("Typy łożek");
        bedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));

        TableColumn<RoomDTO, String> bedsCountColumn = new TableColumn<>("Ilość łóżek w pokoju");
        bedsCountColumn.setCellValueFactory(new PropertyValueFactory<>("bedsCount"));

        TableColumn<RoomDTO, RoomDTO> deleteColumn = new TableColumn<>("Operacje");
        deleteColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>() {

            Button deleteButton = new Button("Usuń");
            Button editButton = new Button("Edytuj");
            HBox hBox = new HBox(deleteButton,editButton);

            @Override
            protected void updateItem(RoomDTO value, boolean empty) {
                super.updateItem(value, empty);

                if (value == null) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                    deleteButton.setOnAction(actionEvent -> {
                        roomService.removeRoom(value.getId());
                        tableView.getItems().remove(value);
                    });
                    editButton.setOnAction(actionEvent -> {
                        Stage stg = new Stage();
                        stg.initModality(Modality.WINDOW_MODAL);
                        stg.initOwner(primaryStage);
                        stg.setScene(new EditRoomScene(stg, tableView, value).getMainScene());
                        stg.setTitle("Dodaj nowy pokój");

                        stg.showAndWait();
                    });
                }
            }

        });

        tableView.getColumns().addAll(numberColumn, bedsColumn, bedsCountColumn, deleteColumn);

        List<RoomDTO> allAsDTO = roomService.getAllAsDTO();

        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    public Tab getRoomTab() {
        return roomTab;
    }
}
