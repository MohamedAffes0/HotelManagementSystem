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
		super(
				new NumberPopupField("Capacité"),
				new FloatPopupField("Prix"));

		setTitle("Modifier Une Chambre");
	}

	@Override
	protected Room dataFromFields() {
		Room room = (Room) getData();

		int capacity = (int) getField(CAPACITY).getValue();
		float price = (float) getField(PRICE).getValue();

		return new Room(room.getId(), room.getRoomType(), room.getFloor(), capacity, price, RoomState.LIBRE);
	}

	@Override
	public void update(Model newData) throws ControllerException {
		if (!(newData instanceof Room))
			throw new ControllerException("Invalid data received");
		
		Controller.getInstance().getRoomManager().update(getData().getId(), (Room) newData);
	}

	@Override
	public void delete() {
		try {
			Controller.getInstance().getRoomManager().delete(getData().getId());
		} catch (DBException exception) {
			setErrorMessage(exception.toString());
		}
	}

	@Override
	public void fieldsFromData() {
		Room room = (Room) getData();

		((NumberPopupField) getField(CAPACITY)).setValue(room.getCapacity());
		((FloatPopupField) getField(PRICE)).setValue(room.getPrice());
	}

}
