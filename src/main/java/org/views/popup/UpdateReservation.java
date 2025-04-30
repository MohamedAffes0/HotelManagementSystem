package org.views.popup;

import java.time.LocalDate;

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
		// Champs de saisie pour la réservation
		super(new DatePopupField("Date de début"),
			new DatePopupField("Date de fin"),
			new ComboBoxPopupField("Statut",
			FXCollections.observableArrayList("Payé", "Non payé")));

		setTitle("Modifier la réservation"); // Titre de la fenêtre contextuelle

	}

	@Override
	public void setData(Model data) {
		super.setData(data); // Appel de la méthode de la classe parente pour définir les données

		// Vérifier si la reservation est de maintenance (le client est null) pour déterminer les champs à afficher
		if (((Reservation) getData()).getHotelClient() == null) {
			setFields(new DatePopupField("Date de début"),
			new DatePopupField("Date de fin"));
		}
		else {
			setFields(
				new DatePopupField("Date de début"),
				new DatePopupField("Date de fin"),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Payé", "Non payé")));
		}
	}

	@Override
	protected Reservation dataFromFields() {
		Reservation currentData = (Reservation) getData(); // Récupérer les données actuelles de la réservation
		boolean reservationStatus = ((Reservation) getData()).isPaid();

		// Vérifier si le reservation n'est pas de type maintenance pour déterminer le statut de la réservation
		if (((Reservation)getData()).getHotelClient() != null) {
			switch ((String) getField(STATUS).getValue()) {
				case "Payé":
					reservationStatus = true;
					break;
				case "Non payé":
					reservationStatus = false;
					break;
			}
		}

		// Récupérer les dates de début et de fin de la réservation
		// Convertir les dates de LocalDate à java.sql.Date
		java.sql.Date startDate = java.sql.Date.valueOf((LocalDate) getField(START_DATE).getValue()); 
		java.sql.Date endDate = java.sql.Date.valueOf((LocalDate) getField(END_DATE).getValue());

		return new Reservation(currentData.getId(), startDate, endDate, reservationStatus,
				currentData.getEmployee(), currentData.getHotelClient(), currentData.getRoom());
	}

	@Override
	public void update(Model newData) throws ControllerException{
		// Vérifier si les données reçues sont valides
		if (!(newData instanceof Reservation))
			throw new ControllerException("Invalid data received");

		// Mettre à jour la réservation dans la base de données
		Controller.getInstance().getReservationManager().update(getData().getId(), (Reservation) newData);
	}

	@Override
	public void delete() {
		try {
			// Supprimer la réservation de la base de données
			Controller.getInstance().getReservationManager().delete(getData().getId());
		} catch (DBException exception) {
			setErrorMessage(exception.toString());
		}
	}

	@Override
	public void fieldsFromData() {
		// Remplir les champs de la fenêtre contextuelle avec les données de la réservation
		Reservation reservation = (Reservation) getData();
		// Vérifier si la réservation est de maintenance (le client est null) pour déterminer les champs à afficher
		if (reservation.getHotelClient() != null) {
			if (reservation.isPaid()) {
				((ComboBoxPopupField) getField(STATUS)).setValue("Payé");
			} else {
				((ComboBoxPopupField) getField(STATUS)).setValue("Non payé");
			}
		}

		// Remplir les champs de date avec les valeurs de la réservation
		// Convertir les dates de java.sql.Date à LocalDate
		((DatePopupField) getField(START_DATE)).setValue(((java.sql.Date) reservation.getStartDate()).toLocalDate());
		((DatePopupField) getField(END_DATE)).setValue(((java.sql.Date) reservation.getEndDate()).toLocalDate());
	}
}
