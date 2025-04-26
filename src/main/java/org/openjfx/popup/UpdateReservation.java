package org.openjfx.popup;

import java.time.LocalDate;
import java.util.Date;

import org.app.reservation.ReservationDelete;
import org.app.reservation.ReservationModify;
import org.models.Model;
import org.models.Reservation;
import org.openjfx.popupfield.ComboBoxPopupField;
import org.openjfx.popupfield.DatePopupField;

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
	protected void dataFromFields() {
		boolean reservationStatus = false;
		switch ((String) getField(STATUS).getValue()) {
			case "Payé":
				reservationStatus = true;
				break;
			case "Non payé":
				reservationStatus = false;
				break;
		}

		Reservation reservation = (Reservation) getData();
        reservation.setPaid(reservationStatus);
        reservation.setStartDate(java.sql.Date.valueOf((LocalDate) getField(START_DATE).getValue()));
        reservation.setEndDate(java.sql.Date.valueOf((LocalDate) getField(END_DATE).getValue()));
	}

	@Override
	public boolean update(Model newData) {
		if (!(newData instanceof Reservation))
			throw new RuntimeException("Invalid data received");
		Reservation reservation = (Reservation) newData;

        return ReservationModify.reservationModify(reservation.getId(), reservation.getRoom(), reservation.getStartDate(), reservation.getEndDate(), reservation.isPaid());
	}

	@Override
	public void delete() {
        ReservationDelete.reservationDelete(((Reservation)getData()).getId());
	}

	@Override
	public void fieldsFromData() {
		Reservation reservation = (Reservation)getData();
        if (reservation.isPaid()) {
            ((ComboBoxPopupField)getField(STATUS)).setValue("Payé");
        } else {
            ((ComboBoxPopupField)getField(STATUS)).setValue("Non payé");
        }

        ((DatePopupField)getField(START_DATE)).setValue(reservation.getStartDate());
        ((DatePopupField)getField(END_DATE)).setValue(reservation.getEndDate());
	}
}
