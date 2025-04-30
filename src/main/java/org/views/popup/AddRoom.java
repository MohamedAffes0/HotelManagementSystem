package org.views.popup;

import org.models.Model;
import org.models.Room;
import org.models.Room.RoomState;
import org.models.Room.RoomType;
import org.views.popupfield.ComboBoxPopupField;
import org.views.popupfield.NumberPopupField;
import org.views.popupfield.FloatPopupField;
import org.controllers.Controller;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

/**
 * AddRoom
 */
public class AddRoom extends AddPopup {
	final int ID = 0;
	final int FLOOR = 1;
	final int CAPACITY = 2;
	final int PRICE = 3;
	final int TYPE = 4;

	public AddRoom() {
		super(
				new NumberPopupField("Numéro"),
				new NumberPopupField("Etage"),
				new NumberPopupField("Capacité"),
				new FloatPopupField("Prix"),
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

		return new Room((int) getField(ID).getValue(), roomType, (int) getField(FLOOR).getValue(),
				(int) getField(CAPACITY).getValue(), (float) getField(PRICE).getValue(), RoomState.LIBRE);
	}

	@Override
	protected void addData(Model newData) throws Exception{
		Controller.getInstance().getRoomManager().insert((Room) newData);
		Stage stage = (Stage) getWindow();
		stage.close();
	}

}
