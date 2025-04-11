package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import javafx.collections.FXCollections;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.room.RoomFilter;
import org.app.room.RoomSelect;
import org.app.StringNumberExtract;

import java.util.ArrayList;

import org.models.RoomModel;
import org.models.RoomModel.RoomState;

public class Rooms implements Initializable {
	private ArrayList<RoomModel> rooms;

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

		list.getChildren().clear();

		try {
			rooms = RoomSelect.roomSelect();
			updateList();
		} catch (Exception e) {
			System.out.println("Erreur de connection a la base de donnée");
		}
	}

	@FXML
	private void updateList() throws Exception {
		// System.out.println(filter.getValue());

		ArrayList<RoomModel> roomsTodisplay = getRoomsArray();

		list.getChildren().clear();
		for (RoomModel data : roomsTodisplay) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/roomButton.fxml"));
			list.getChildren().add(loader.load());
			RoomButton controller = loader.getController();
			controller.setData(data);
		}
	}

	private ArrayList<RoomModel> getRoomsArray() {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return rooms;
		}

		// String is not empty so check filter type
		switch (filter.getValue()) {
			case "Etage":
				// Replace all nume
				search.setText(StringNumberExtract.extract(searchText));
				return RoomFilter.FilterByFloor(rooms, Integer.parseInt(search.getText()));
			case "Nombre De Personnes":
				search.setText(StringNumberExtract.extract(searchText));
				return RoomFilter.FilterByNumberOfPeople(rooms, Integer.parseInt(search.getText()));
			case "Prix":
				search.setText(StringNumberExtract.extract(searchText));
				return RoomFilter.FilterByPrice(rooms, Integer.parseInt(search.getText()));
			case "Type":
				return RoomFilter.FilterByType(rooms, search.getText());
			case "Etat":
				RoomState state = stateFromSearch();

				if (state == null) {
					return RoomSelect.roomSelect();
				} else {
					return RoomFilter.FilterByState(rooms, state);
				}
			default:
				return RoomSelect.roomSelect();
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

		return null;
	}
}
