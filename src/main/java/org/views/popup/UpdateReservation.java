package org.views.popup;

import java.time.LocalDate;

import org.controllers.reservation.ReservationDelete;
import org.controllers.reservation.ReservationModify;
import org.controllers.user.ControllerException;
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
			throw new RuntimeException("Invalid data received");
		Reservation reservation = (Reservation) newData;

		ReservationModify.reservationModify(reservation.getId(), reservation.getRoom(),
				reservation.getStartDate(), reservation.getEndDate(), reservation.isPaid());
	}

	@Override
	public void delete() {
		ReservationDelete.reservationDelete(((Reservation) getData()).getId());
	}

	@Override
	public void fieldsFromData() {
		Reservation reservation = (Reservation) getData();
		if (reservation.isPaid()) {
			((ComboBoxPopupField) getField(STATUS)).setValue("Payé");
		} else {
			((ComboBoxPopupField) getField(STATUS)).setValue("Non payé");
		}

		((DatePopupField) getField(START_DATE)).setValue(reservation.getStartDate());
		((DatePopupField) getField(END_DATE)).setValue(reservation.getEndDate());
	}
}
