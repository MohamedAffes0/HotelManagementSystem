package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;

import org.app.room.RoomModify;
import org.app.room.RoomSelect;
import org.models.Room.RoomState;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import javafx.fxml.Initializable;

public class UpdateRoom implements Initializable {
	public int id = 0;
	public int floor = 0;

	@FXML
	private Spinner<Integer> numberOfPeople;

	@FXML
	private Spinner<Double> price;

	@FXML
	private ComboBox<String> state;

	@FXML
	private Label title;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		state.setItems(FXCollections.observableArrayList("Libre", "Occupée", "Maintenance"));
		state.setValue("Libre");
		numberOfPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999_999, 0));
		price.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999_999.999, 0.0, 0.1));
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) title.getScene().getWindow();
		stage.close();
	}

	@FXML
	void confirm(ActionEvent event) {
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

		if (RoomModify.roomModify(id, numberOfPeople.getValue().intValue(), price.getValue().floatValue(),
				roomState)) {
			Stage stage = (Stage) title.getScene().getWindow();
			stage.close();
			Rooms.rooms = RoomSelect.roomSelect();
		}
	}

	@FXML
	void delete(ActionEvent event) {
		System.out.println("YA BHIM");
	}

}
