package org.views.popup;

import java.time.LocalDate;
import java.time.ZoneId;

import org.controllers.Controller;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Model;
import org.models.Reservation;
import org.views.popupfield.ComboBoxPopupField;
import org.views.popupfield.DatePopupField;

import javafx.collections.FXCollections;

public class UpdateReservation extends UpdatePopup {
	final int START_DATE = 0;
	final int END_DATE = 1;
	final int STATUS = 2;

	public UpdateReservation() {
		super(
				new DatePopupField("Date de début"),
				new DatePopupField("Date de fin"),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Payé", "Non payé")));

		setTitle("Modifier la réservation");
	}

	@Override
	protected Reservation dataFromFields() {
		Reservation currentData = (Reservation) getData();
		boolean reservationStatus = false;
		switch ((String) getField(STATUS).getValue()) {
			case "Payé":
				reservationStatus = true;
				break;
			case "Non payé":
				reservationStatus = false;
				break;
		}
		java.sql.Date startDate = java.sql.Date.valueOf((LocalDate) getField(START_DATE).getValue());
		java.sql.Date endDate = java.sql.Date.valueOf((LocalDate) getField(END_DATE).getValue());

		return new Reservation(currentData.getId(), startDate, endDate, reservationStatus,
				currentData.getEmployee(), currentData.getHotelClient(), currentData.getRoom());
	}

	@Override
	public void update(Model newData) throws ControllerException{
		if (!(newData instanceof Reservation))
			throw new ControllerException("Invalid data received");

		Controller.getInstance().getReservationManager().update(getData().getId(), (Reservation) newData);
	}

	@Override
	public void delete() {
		try {
			Controller.getInstance().getReservationManager().delete(getData().getId());
		} catch (DBException exception) {
			setErrorMessage(exception.toString());
		}
	}

	@Override
	public void fieldsFromData() {
		Reservation reservation = (Reservation) getData();
		if (reservation.isPaid()) {
			((ComboBoxPopupField) getField(STATUS)).setValue("Payé");
		} else {
			((ComboBoxPopupField) getField(STATUS)).setValue("Non payé");
		}

		((DatePopupField) getField(START_DATE)).setValue(((java.sql.Date) reservation.getStartDate()).toLocalDate());
		((DatePopupField) getField(END_DATE)).setValue(((java.sql.Date) reservation.getEndDate()).toLocalDate());
	}
}
