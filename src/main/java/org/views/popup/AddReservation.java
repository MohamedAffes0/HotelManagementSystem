package org.views.popup;

import java.time.LocalDate;
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
	protected void addData(Model newData) throws Exception{
		Controller.getInstance().getReservationManager().insert((Reservation) newData);
		Stage stage = (Stage) getWindow();
		stage.close();
	}
}
