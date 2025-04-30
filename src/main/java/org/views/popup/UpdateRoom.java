package org.views.popup;

import org.controllers.Controller;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Model;
import org.models.Room;
import org.models.Room.RoomState;
import org.views.popupfield.FloatPopupField;
import org.views.popupfield.NumberPopupField;

/**
 * UpdateRoom
 */
public class UpdateRoom extends UpdatePopup {
	final int CAPACITY = 0;
	final int PRICE = 1;

	public UpdateRoom() {
		// Champs de saisie pour la chambre
		super(
				new NumberPopupField("Capacité"),
				new FloatPopupField("Prix"));

		setTitle("Modifier Une Chambre"); // Titre de la fenêtre contextuelle
	}

	@Override
	protected Room dataFromFields() {
		Room room = (Room) getData(); // Récupérer les données actuelles de la chambre

		int capacity = (int) getField(CAPACITY).getValue();
		float price = (float) getField(PRICE).getValue();

		return new Room(room.getId(), room.getRoomType(), room.getFloor(), capacity, price, RoomState.LIBRE);
	}

	@Override
	public void update(Model newData) throws ControllerException {
		// Vérifier si les données reçues sont valides
		if (!(newData instanceof Room))
			throw new ControllerException("Invalid data received");
		
		// Mettre à jour la chambre dans la base de données
		Controller.getInstance().getRoomManager().update(getData().getId(), (Room) newData);
	}

	@Override
	public void delete() {
		try {
			// Supprimer la chambre de la base de données
			Controller.getInstance().getRoomManager().delete(getData().getId());
		} catch (DBException exception) {
			// Afficher le message d'erreur dans la fenêtre contextuelle
			setErrorMessage(exception.toString());
		}
	}

	@Override
	public void fieldsFromData() {
		Room room = (Room) getData(); // Récupérer les données de la chambre

		// Remplir les champs de la fenêtre contextuelle avec les données de la chambre
		((NumberPopupField) getField(CAPACITY)).setValue(room.getCapacity());
		((FloatPopupField) getField(PRICE)).setValue(room.getPrice());
	}

}
