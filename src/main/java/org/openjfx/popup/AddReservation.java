package org.openjfx.popup;

import java.time.LocalDate;
import java.sql.Date;

import org.models.Model;
import org.models.Reservation;
import org.openjfx.popupfield.ComboBoxPopupField;
import org.openjfx.popupfield.DatePopupField;
import org.openjfx.popupfield.NumberPopupField;
import org.app.reservation.ReservationAdd;
import org.app.room.RoomSelect;
import org.app.reservation.ReservationAdd.CreationStatus;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class AddReservation extends AddPopup {
	final int START_DATE = 0;
	final int END_DATE = 1;
	final int CLIENT = 2;
	final int ROOM = 3;
	final int STATUS = 4; // payé ou non payé
	// final int EMPLOYEE = 5;

	public AddReservation() {
		super(
				new DatePopupField("Date de début"),
				new DatePopupField("Date de fin"),
				new NumberPopupField("Client"),
				new NumberPopupField("Chambre"),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Payé", "Non payé")));

		setTitle("Ajouter une réservation");
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

		LocalDate startDate = (LocalDate) getField(START_DATE).getValue();
		LocalDate endDate = (LocalDate) getField(END_DATE).getValue();
		return new Reservation(
				1,
				Date.valueOf(startDate),
				Date.valueOf(endDate),
				status,
				3,
				(int) getField(CLIENT).getValue(),
				(int) getField(ROOM).getValue());
	}

	@Override
	protected void addData(Model newData) {
		CreationStatus result = ReservationAdd.reservationAdd((Reservation) newData, RoomSelect.dataFromDB());

		switch (result) {
			case SUCCESS:
				// Close the window after adding the room
				Stage stage = (Stage) getWindow();
				stage.close();
				break;
			case DB_PROBLEM:
				break;
			case ROOM_NON_EXISTENT:
				System.out.println("Room does not exist");
				break;
			case ROOM_NOT_AVAILABLE:
				System.out.println("Room not available");
				break;
			case DATE_NOT_AVAILABLE:
				System.out.println("Date not available");
				break;
			case CLIENT_NON_EXISTENT:
				System.out.println("Client does not exist");
				break;
		}
	}
}
