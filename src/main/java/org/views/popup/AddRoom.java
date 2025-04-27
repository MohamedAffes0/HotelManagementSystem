package org.views.popup;

import org.models.Model;
import org.models.Room;
import org.app.room.RoomAdd;
import org.app.room.RoomAdd.CreationStatus;
import org.app.room.RoomSelect;
import org.models.Room.RoomState;
import org.models.Room.RoomType;
import org.views.popupfield.ComboBoxPopupField;
import org.views.popupfield.NumberPopupField;
import org.views.popupfield.FloatPopupField;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * AddRoom
 */
public class AddRoom extends AddPopup {
	final int ID = 0;
	final int FLOOR = 1;
	final int CAPACITY = 2;
	final int PRICE = 3;
	final int STATE = 4;
	final int TYPE = 5;

	public AddRoom() {
		super(
				new NumberPopupField("Numéro"),
				new NumberPopupField("Etage"),
				new NumberPopupField("Capacité"),
				new FloatPopupField("Prix"),
				new ComboBoxPopupField("Etat",
						FXCollections.observableArrayList("Libre", "Occupée", "Maintenance")),
				new ComboBoxPopupField("Type",
						FXCollections.observableArrayList("Simple", "Double", "Suite")));
		setTitle("Ajouter une chambre");
	}

	@Override
	protected Model dataFromFields() {
		RoomType roomType = RoomType.SIMPLE;
		switch ((String) getField(TYPE).getValue()) {
			case "Simple":
				roomType = RoomType.SIMPLE;
				break;
			case "Double":
				roomType = RoomType.DOUBLE;
				break;
			case "Suite":
				roomType = RoomType.SUITE;
				break;
		}
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

		return new Room((int) getField(ID).getValue(), roomType, (int) getField(FLOOR).getValue(),
				(int) getField(CAPACITY).getValue(), (float) getField(PRICE).getValue(), roomState);
	}

	@Override
	protected void addData(Model newData) {
		CreationStatus result = RoomAdd.roomAdd((Room) newData, RoomSelect.dataFromDB());

		switch (result) {
			case SUCCESS:
				// Close the window after adding the room
				Stage stage = (Stage) getWindow();
				stage.close();
				break;
			case DB_PROBLEM:
				break;
			case ID_EXISTS:
				System.out.println("Exists");
				break;
			case EMPTY_FIELD:
				System.out.println("Empty field");
				break;
		}
	}

}
