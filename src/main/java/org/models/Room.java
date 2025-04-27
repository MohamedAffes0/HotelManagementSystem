package org.models;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import org.app.StringFloatExtract;
import org.app.StringNumberExtract;
import org.app.room.RoomFilter;

public class Room extends Model {

	public static enum RoomState {
		LIBRE,
		OCCUPEE,
		MAINTENANCE
	}

	public static enum RoomType {
		SIMPLE,
		DOUBLE,
		SUITE
	}

	private int id;
	private RoomType roomType;
	private int floor;
	private int capacity;
	private float price;
	private RoomState state;

	public Room(int id, RoomType roomType, int floor, int capacity, float price, RoomState state) {

		if (id <= 0) {
			throw new IllegalArgumentException("L'ID ne doit pas être vide.");
		}

		this.id = id;
		this.roomType = roomType;
		this.floor = floor;
		this.capacity = capacity;
		this.price = price;
		this.state = state;
	}

	public Room() {
	}

	@Override
	public boolean filter(TextField search, String filterType) {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return true;
		}

		// String is not empty so check filter type
		switch (filterType) {
			case "Etage":
				// Replace all nume
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return RoomFilter.filterByFloor(this, Integer.parseInt(search.getText()));
			case "Capacité":
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return RoomFilter.filterByNumberOfPeople(this, Integer.parseInt(search.getText()));
			case "Prix":
				search.setText(StringFloatExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				// return RoomFilter.filterByPrice(this, Integer.parseInt(search.getText()) +
				// 0.999f);
				return RoomFilter.filterByPrice(this, Float.parseFloat(search.getText()));
			case "Type":
				RoomType type = typeFromSearch(searchText);

				if (type == null) {
					return true;
				} else {
					return RoomFilter.filterByType(this, type);
				}
			case "Etat":
				RoomState state = stateFromSearch(searchText);

				if (state == null) {
					return true;
				} else {
					return RoomFilter.filterByState(this, state);
				}
			default:
				return true;
		}
	}

	@Override
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		data.add(new ModelField("Chambre " + getId(), "room-badge"));
		data.add(new ModelField("Etage " + getFloor(), "floor-badge"));
		data.add(new ModelField(getTypeString(), "room-type-badge"));
		data.add(new ModelField(String.valueOf(getCapacity()), "person-badge", createIcon("M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10s-3.516.68-4.168 1.332c-.678.678-.83 1.418-.832 1.664z", 
			0.8, "#2f2f2f"), ContentDisplay.RIGHT));
		data.add(new ModelField(getPrice() + " Dt", "price-badge"));
		String styleClass = "";
		switch (state) {
			case LIBRE:
				styleClass = "label-libre";
				break;
			case OCCUPEE:
				styleClass = "label-occupee";
				break;
			case MAINTENANCE:
				styleClass = "label-maintenance";
				break;
		}
		data.add(new ModelField(getStateString(), styleClass));

		return data;
	}

	// Getters
	public int getId() {
		return id;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public int getFloor() {
		return floor;
	}

	public int getCapacity() {
		return capacity;
	}

	public float getPrice() {
		return price;
	}

	public RoomState getState() {
		return state;
	}

	// Setters
	public void setPrice(float price) {
		this.price = price;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setState(RoomState state) {
		this.state = state;
	}

	public String getTypeString() {
		switch (roomType) {
			case SIMPLE:
				return "Simple";
			case SUITE:
				return "Suite";
			case DOUBLE:
				return "Double";
			default:
				return "Simple";
		}
	}

	public String getStateString() {
		switch (state) {
			case LIBRE:
				return "Libre";
			case OCCUPEE:
				return "Occupée";
			case MAINTENANCE:
				return "Maintenance";
			default:
				return "libre";
		}
	}

	private RoomState stateFromSearch(String searchText) {
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

	private RoomType typeFromSearch(String searchText) {
		if (searchText.isEmpty()) {
			return null;
		}

		searchText = searchText.toLowerCase();

		// Return null if searchText = "s" because "sinple" and "suite" both start with
		// "s"
		if (searchText.equals("s")) {
			return null;
		}

		// Simple
		if ("simple".contains(searchText)) {
			return RoomType.SIMPLE;
		}

		if (searchText.contains("simple")) {
			return RoomType.SIMPLE;
		}

		// Suite
		if (searchText.contains("suite")) {
			return RoomType.SUITE;
		}

		if ("suite".contains(searchText)) {
			return RoomType.SUITE;
		}

		// Double
		if ("double".contains(searchText)) {
			return RoomType.DOUBLE;
		}

		if (searchText.contains("double")) {
			return RoomType.DOUBLE;
		}
		return null;
	}

}
