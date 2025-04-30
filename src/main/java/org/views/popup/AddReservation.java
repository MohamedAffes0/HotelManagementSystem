package org.views.popup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

import org.models.Model;
import org.models.Reservation;
import org.views.popupfield.ComboBoxPopupField;
import org.views.popupfield.DatePopupField;
import org.views.popupfield.NumberPopupField;
import org.controllers.Controller;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class AddReservation extends AddPopup {
	final int START_DATE = 0;
	final int END_DATE = 1;
	final int CAPACITY = 2;
	final int ROOM = 3;
	final int IS_MAINTENANCE = 4;
	final int CLIENT = 5;
	final int STATUS = 6; // payé ou non payé

	public AddReservation() {
		super(
				new DatePopupField("Date de début"),
				new DatePopupField("Date de fin"),
				new NumberPopupField("Capacité"),
				new ComboBoxPopupField("Chambre"),
				new ComboBoxPopupField("Type",
						FXCollections.observableArrayList("Client", "En maintenance")),
				new NumberPopupField("Client"),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Payé", "Non payé")));
		((DatePopupField) getField(START_DATE)).setOnAction(event -> updateRooms());
		((DatePopupField) getField(END_DATE)).setOnAction(event -> updateRooms());
		((NumberPopupField) getField(CAPACITY)).setOnAction(event -> updateRooms());
		((ComboBoxPopupField) getField(ROOM)).setValue("Aucune chambre");
		setTitle("Ajouter une réservation");
	}

	public void updateRooms() {
		if (getField(START_DATE).getValue() == null || getField(END_DATE).getValue() == null) {
			return;
		}
		LocalDate startDate = (LocalDate) getField(START_DATE).getValue();
		LocalDate endDate = (LocalDate) getField(END_DATE).getValue();
		int capacity = (int) getField(CAPACITY).getValue();
		try {
			ArrayList<Integer> availableRooms = Controller.getInstance().getRoomManager()
					.getAvaliableRooms(Date.valueOf(startDate), Date.valueOf(endDate), capacity);
			if (availableRooms.isEmpty()) {
				// Pour passer au bloc catch
				throw new Exception("Aucune chambre");
			}
			String[] rooms = new String[availableRooms.size()];
			String currentValue = (String) getField(ROOM).getValue();
			boolean isValidValue = false;

			int i = 0;
			for (Integer roomId : availableRooms) {
				if (rooms[i] == currentValue) {
					isValidValue = true;
				}
				rooms[i++] = "Chambre " + roomId.toString();
			}

			((ComboBoxPopupField) getField(ROOM)).setItems(rooms);
			if (isValidValue) {
				((ComboBoxPopupField) getField(ROOM)).setValue(currentValue);
			}
		} catch (Exception exception) {
			((ComboBoxPopupField) getField(ROOM)).setItems("Aucune chambre");
			((ComboBoxPopupField) getField(ROOM)).setValue("Aucune chambre");
		}
	}

	@Override
	protected Model dataFromFields() {
		boolean status;
		switch ((String) getField(STATUS).getValue()) {
			case "Payé":
				status = true;
				break;
			case "Non payé":
				status = false;
				break;
			default:
				status = false;
				break;
		}

		int room = 0;
		String roomString = ((String) getField(ROOM).getValue());
		if (roomString != null && roomString.contains("Chambre ")) {
			room = Integer.parseInt(roomString.replace("Chambre ", ""));
		}

		boolean isMaintenance = false;
		switch ((String) getField(IS_MAINTENANCE).getValue()) {
			case "Client":
				isMaintenance = false;
				break;
			case "En maintenance":
				isMaintenance = true;
				break;
			default:
				isMaintenance = false;
				break;
		}

		return new Reservation(
				1,
				getField(START_DATE).getValue() == null ? null : Date.valueOf((LocalDate) getField(START_DATE).getValue()),
				getField(END_DATE).getValue() == null ? null : Date.valueOf((LocalDate) getField(END_DATE).getValue()),
				status,
				Controller.getInstance().getCurrentUser(),
				isMaintenance? null : (int) getField(CLIENT).getValue(),
				room);
	}

	@Override
	protected void addData(Model newData) throws Exception {
		Controller.getInstance().getReservationManager().insert((Reservation) newData);
		Stage stage = (Stage) getWindow();
		stage.close();
	}
}
