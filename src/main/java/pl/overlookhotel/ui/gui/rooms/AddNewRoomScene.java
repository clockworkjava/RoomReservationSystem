package pl.overlookhotel.ui.gui.rooms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.room.RoomService;
import pl.overlookhotel.domain.room.dto.RoomDTO;
import pl.overlookhotel.util.SystemUtils;

import java.util.ArrayList;
import java.util.List;

public class AddNewRoomScene {

    private final Scene mainScene;
    private final List<ComboBox<String>> comboBoxes = new ArrayList<>();
    private final RoomService roomService = ObjectPool.getRoomService();

    public AddNewRoomScene(Stage stg, TableView<RoomDTO> tableView) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);

        Label roomNumberLabel = new Label("Numer pokoju:");
        TextField roomNumberField = new TextField();

        roomNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")){
            roomNumberField.setText(oldValue);
        }
        });

        gridPane.add(roomNumberLabel, 0, 0);
        gridPane.add(roomNumberField, 1, 0);

        Label bedTypeLabel = new Label("Typ łożka");

        Button addNewBedButton = new Button();
        Image icon = new Image(getClass().getClassLoader().getResourceAsStream("add.jpg"));
        ImageView imgView = new ImageView(icon);
        imgView.setFitWidth(16);
        imgView.setFitHeight(16);
        addNewBedButton.setGraphic(imgView);
        addNewBedButton.setPadding(Insets.EMPTY);

        gridPane.add(bedTypeLabel, 0, 1);
        gridPane.add(addNewBedButton, 1, 1);

        VBox bedsVerticalLayout = new VBox(getComboBox());

        addNewBedButton.setOnAction(actionEvent -> bedsVerticalLayout.getChildren().add(getComboBox()));

        Button addNewRoomButton = new Button("Dodaj nowy pokój");
        addNewRoomButton.setOnAction(actionEvent -> {
            int newRoomNumber = Integer.parseInt(roomNumberField.getText());
            List<String> bedTypes = new ArrayList<>();

            this.comboBoxes.forEach(comboBox -> {
                bedTypes.add(comboBox.getValue());
            });

            this.roomService.createNewRoom(newRoomNumber, bedTypes);

            tableView.getItems().clear();

            List<RoomDTO> allAsDTO = roomService.getAllAsDTO();
            tableView.getItems().addAll(allAsDTO);

            stg.close();
        });

        addNewBedButton.setPadding(new Insets(5, 5, 5, 5));

        gridPane.add(bedsVerticalLayout, 1, 2);
        gridPane.add(addNewRoomButton, 0, 3);

        this.mainScene = new Scene(gridPane, 640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }

    private ComboBox<String> getComboBox() {
        ComboBox<String> bedTypeField = new ComboBox<>();
        bedTypeField.getItems().addAll(
                SystemUtils.SINGLE_BED,
                SystemUtils.DOUBLE_BED,
                SystemUtils.KING_SIZE_BED);
        bedTypeField.setValue(SystemUtils.SINGLE_BED);
        this.comboBoxes.add(bedTypeField);
        return bedTypeField;
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
