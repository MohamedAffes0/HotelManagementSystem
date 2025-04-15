package org.openjfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.room.RoomAdd;
import org.app.room.RoomAdd.CreationStatus;
import org.models.RoomModel;
import org.models.RoomModel.RoomState;
import org.models.RoomModel.RoomType;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class AddRoom implements Initializable {

	@FXML
	private Spinner<Integer> floor;

	@FXML
	private Spinner<Integer> id;

	@FXML
	private Spinner<Integer> numberOfPeople;

	@FXML
	private Spinner<Double> price;

	@FXML
	private ComboBox<String> state;

	@FXML
	private ComboBox<String> type;

	@FXML
	void addRoom(ActionEvent event) {
		RoomType roomType = RoomType.SIMPLE;
		switch (type.getValue()) {
			case "Simple":
				roomType = RoomType.SIMPLE;
				break;
			case "Double":
				roomType = RoomType.DOUBLE;
				break;
			case "Suite":
				roomType = RoomType.SUITE;
				break;
		}
		RoomState roomState = RoomState.LIBRE;
		switch (state.getValue()) {
			case "Libre":
				roomState = RoomState.LIBRE;
				break;
			case "Occupée":
				roomState = RoomState.OCCUPEE;
				break;
			case "Maintenance":
				roomState = RoomState.MAINTENANCE;
				break;
		}

		RoomModel room = new RoomModel(id.getValue().intValue(), roomType, floor.getValue().intValue(),
				numberOfPeople.getValue().intValue(), price.getValue().floatValue(), roomState);

		CreationStatus result = RoomAdd.roomAdd(room, Rooms.rooms);

		switch (result) {
			case SUCCESS:
				Rooms.rooms.add(room);
				// Close the window after adding the room
				Stage stage = (Stage) floor.getScene().getWindow();
				stage.close();
				break;
			case DB_PROBLEM:
				break;
			case ID_EXISTS:
				break;
		}
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) floor.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		state.setItems(FXCollections.observableArrayList("Libre", "Occupée", "Maintenance"));
		state.setValue("Libre");

		type.setItems(FXCollections.observableArrayList("Simple", "Suite", "Double"));
		type.setValue("Simple");

		id.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999_999, 1));
		floor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999_999, 0));
		numberOfPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999_999, 0));
		price.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999_999.999, 0.0, 0.1));
	}
}
