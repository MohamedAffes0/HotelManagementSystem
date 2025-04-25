package org.openjfx.popup;

import org.app.room.RoomModify;
import org.models.Model;
import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;
import org.openjfx.popupfield.ComboBoxPopupField;
import org.openjfx.popupfield.FloatPopupField;
import org.openjfx.popupfield.NumberPopupField;

import javafx.collections.FXCollections;

/**
 * UpdateRoom
 */
public class UpdateRoom extends UpdatePopup {
	final int CAPACITY = 3;
	final int PRICE = 3;
	final int STATE = 3;

	public UpdateRoom(int id) {
		super(id,
				new NumberPopupField("Capacité"),
				new FloatPopupField("Prix"),
				new ComboBoxPopupField("Etat",
						FXCollections.observableArrayList("Libre", "Occupée", "Maintenance")));

		setTitle("Modifier Une Chambre");
	}

	@Override
	public Room getData() {
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

		return new Room(getId(), RoomType.SIMPLE, 1, (int) getField(CAPACITY).getValue(),
				(float) getField(PRICE).getValue(), roomState);
	}

	@Override
	public void update(Model newData) {
		if (!(newData instanceof Room))
			throw new RuntimeException("Invalid data received");
		Room room = (Room) newData;

		RoomModify.roomModify(getId(), room.getCapacity(), room.getPrice(), room.getState());
	}

}
