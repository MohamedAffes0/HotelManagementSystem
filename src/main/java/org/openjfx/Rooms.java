package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import javafx.collections.FXCollections;

import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.room.RoomFilter;
import org.app.room.RoomSelect;
import org.app.StringNumberExtract;

import java.util.ArrayList;

import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;

public class Rooms implements Initializable {
	public static ArrayList<Room> rooms;

	@FXML
	private ComboBox<String> filter;

	@FXML
	private VBox list;
	@FXML
	private TextField search;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filter.setItems(FXCollections.observableArrayList("Etage", "Type", "Nombre De Personnes", "Prix",
				"Etat"));

		filter.setValue("Etage");

		try {
			rooms = RoomSelect.roomSelect();
			updateList();
		} catch (Exception e) {
			System.out.println("Erreur de connection a la base de donnée");
		}
	}

	@FXML
	void addRoom(ActionEvent event) {
		try {
			Parent content = FXMLLoader.load(getClass().getResource("/addRoom.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(content);
			stage.setResizable(false);

			stage.setTitle("Ajouter une chambre");
			stage.setScene(scene);
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						rooms = RoomSelect.roomSelect();
						updateList();
					} catch (Exception exception) {
						System.out.println("Erreur de connection a la base de donnée");
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println("Error opening add room popup");
		}
	}

	@FXML
	private void updateList() throws Exception {
		// System.out.println(filter.getValue());

		list.getChildren().clear();
		for (Room room : rooms) {
			if (!filterRoom(room)) {
				continue;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/roomButton.fxml"));
			list.getChildren().add(loader.load());
			RoomButton controller = loader.getController();
			controller.setData(room);
		}
	}

	// Returns true if the rooom should be displayed according to the current
	// settings.
	private boolean filterRoom(Room room) {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return true;
		}

		// String is not empty so check filter type
		switch (filter.getValue()) {
			case "Etage":
				// Replace all nume
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return RoomFilter.filterByFloor(room, Integer.parseInt(search.getText()));
			case "Nombre De Personnes":
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return RoomFilter.filterByNumberOfPeople(room, Integer.parseInt(search.getText()));
			case "Prix":
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return RoomFilter.filterByPrice(room, Integer.parseInt(search.getText()));
			case "Type":
				RoomType type = typeFromSearch();

				if (type == null) {
					return true;
				} else {
					return RoomFilter.filterByType(room, type);
				}
			case "Etat":
				RoomState state = stateFromSearch();

				if (state == null) {
					return true;
				} else {
					return RoomFilter.filterByState(room, state);
				}
			default:
				return true;
		}
	}

	private RoomState stateFromSearch() {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return null;
		}

		// Occupée
		if ("occupée".contains(searchText.toLowerCase())) {
			return RoomState.OCCUPEE;
		}

		if ("occupee".contains(searchText.toLowerCase())) {
			return RoomState.OCCUPEE;
		}

		if (searchText.toLowerCase().contains("occupée")) {
			return RoomState.OCCUPEE;
		}

		if (searchText.toLowerCase().contains("occupee")) {
			return RoomState.OCCUPEE;
		}

		// Libre
		if ("libre".contains(searchText.toLowerCase())) {
			return RoomState.LIBRE;
		}

		if (searchText.toLowerCase().contains("libre")) {
			return RoomState.OCCUPEE;
		}

		// Maintenance
		if ("maintenance".contains(searchText.toLowerCase())) {
			return RoomState.MAINTENANCE;
		}

		if (searchText.toLowerCase().contains("maintenance")) {
			return RoomState.MAINTENANCE;
		}
		return null;
	}

	private RoomType typeFromSearch() {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return null;
		}

		// Simple
		if ("simple".contains(searchText.toLowerCase())) {
			return RoomType.SIMPLE;
		}

		if (searchText.toLowerCase().contains("simple")) {
			return RoomType.SIMPLE;
		}

		// Suite
		if (searchText.toLowerCase().contains("suite")) {
			return RoomType.SUITE;
		}

		if ("suite".contains(searchText.toLowerCase())) {
			return RoomType.SUITE;
		}

		// Double
		if ("double".contains(searchText.toLowerCase())) {
			return RoomType.DOUBLE;
		}

		if (searchText.toLowerCase().contains("double")) {
			return RoomType.DOUBLE;
		}
		return null;
	}
}
