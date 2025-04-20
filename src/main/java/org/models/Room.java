package org.models;

import javafx.scene.control.TextField;

import java.util.ArrayList;

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
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
				return RoomFilter.filterByPrice(this, Integer.parseInt(search.getText()) + 0.999f);
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

		// Return null if searchText = "s" because "sinple" and "suite" both start with "s"
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

	@Override
	public ArrayList<String> getStringData() {
		ArrayList<String> data = new ArrayList<String>();

		data.add("Chambre " + getId());
		data.add("Etage " + getFloor());
		data.add(getTypeString());
		data.add(getPrice() + " Dt");
		data.add(getCapacity() + " Personnes");
		data.add(getStateString());

		return data;
	}
}
