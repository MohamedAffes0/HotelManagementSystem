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
	final int CLIENT = 4;
	final int STATUS = 5; // payé ou non payé
	// final int EMPLOYEE = 5;

	public AddReservation() {
		super(
				new DatePopupField("Date de début"),
				new DatePopupField("Date de fin"),
				new NumberPopupField("Capacité"),
				new ComboBoxPopupField("Chambre"),
				new NumberPopupField("Client"),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Payé", "Non payé")));
		// ((ComboBoxPopupField)getField(ROOM)).setValue("Veuillez selectionner ");;
		((DatePopupField) getField(START_DATE)).setOnAction(event -> updateRooms());
		setTitle("Ajouter une réservation");
	}

	public void updateRooms() {
		LocalDate startDate = (LocalDate) getField(START_DATE).getValue();
		LocalDate endDate = (LocalDate) getField(END_DATE).getValue();
		int capacity = (int) getField(CAPACITY).getValue();
		try {
			ArrayList<Integer> availableRooms = Controller.getInstance().getRoomManager()
					.getAvaliableRooms(Date.valueOf(startDate), Date.valueOf(endDate), capacity);
			if (availableRooms.isEmpty()) {
				// Pour passer au bloc catch
				throw new Exception();
			}
			String[] rooms = new String[availableRooms.size()];

			int i = 0;
			for (Integer roomId : availableRooms) {
				rooms[i++] = "Chambre " + roomId.toString();
			}

			((ComboBoxPopupField) getField(ROOM)).setItems(rooms);

		} catch (Exception exception) {
			((ComboBoxPopupField) getField(ROOM)).setValue("Aucune chambre disponible");
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
		if (roomString.contains("Chambre ")) {
			room = Integer.parseInt(roomString.replace("Chambre", ""));
		}

		LocalDate startDate = (LocalDate) getField(START_DATE).getValue();
		LocalDate endDate = (LocalDate) getField(END_DATE).getValue();
		return new Reservation(
				1,
				startDate == null ? null : Date.valueOf(startDate),
				endDate == null ? null : Date.valueOf(endDate),
				status,
				3,
				(int) getField(CLIENT).getValue(),
				room);
	}

	@Override
	protected void addData(Model newData) throws Exception {
		Controller.getInstance().getReservationManager().insert((Reservation) newData);
		Stage stage = (Stage) getWindow();
		stage.close();
	}
}
