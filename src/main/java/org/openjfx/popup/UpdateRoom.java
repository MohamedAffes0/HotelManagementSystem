package org.openjfx.popup;

import org.app.room.RoomDelete;
import org.app.room.RoomModify;
import org.models.Model;
import org.models.Room;
import org.models.Room.RoomState;
import org.openjfx.popupfield.ComboBoxPopupField;
import org.openjfx.popupfield.FloatPopupField;
import org.openjfx.popupfield.NumberPopupField;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * UpdateRoom
 */
public class UpdateRoom extends UpdatePopup {
	final int CAPACITY = 0;
	final int PRICE = 1;
	final int STATE = 2;

	public UpdateRoom() {
		super(
				new NumberPopupField("Capacité"),
				new FloatPopupField("Prix"),
				new ComboBoxPopupField("Etat",
						FXCollections.observableArrayList("Libre", "Occupée", "Maintenance")));

		setTitle("Modifier Une Chambre");
	}

	@Override
	protected void dataFromFields() {
		RoomState roomState = RoomState.LIBRE;
		switch ((String) getField(STATE).getValue()) {
			case "Libre":
				roomState = RoomState.LIBRE;
				break;
			case "Occupée":
				roomState = RoomState.OCCUPEE;
				break;
			case "Maintenance":
				roomState = RoomState.MAINTENANCE;
				break;
		}

		Room room = (Room) getData();
		room.setCapacity((int) getField(CAPACITY).getValue());
		room.setPrice((float) getField(PRICE).getValue());
		room.setState(roomState);
	}

	@Override
	public boolean update(Model newData) {
		if (!(newData instanceof Room))
			throw new RuntimeException("Invalid data received");
		Room room = (Room) newData;

		return RoomModify.roomModify(room.getId(), room.getCapacity(), room.getPrice(), room.getState());
	}

	@Override
	public void delete() {
		RoomDelete.roomDelete(((Room)getData()).getId());
	}

	@Override
	public void fieldsFromData() {
		Room room = (Room)getData();
		switch (room.getState()) {
			case LIBRE:
				((ComboBoxPopupField)getField(STATE)).setValue("Libre");
				break;
			case MAINTENANCE:
				((ComboBoxPopupField)getField(STATE)).setValue("Maintenance");
				break;
			case OCCUPEE:
				((ComboBoxPopupField)getField(STATE)).setValue("Occupée");
				break;
			default:
				break;
		}

		((NumberPopupField)getField(CAPACITY)).setValue(room.getCapacity());
		((FloatPopupField)getField(PRICE)).setValue(room.getPrice());
	}

}
