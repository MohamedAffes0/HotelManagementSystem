package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.models.Model;
import org.models.Room;
import javafx.scene.control.Button;

public class RoomButton extends ListButton{

    @FXML
    public Button button;

    @FXML
    private Label floor;

    @FXML
    private Label id;

    @FXML
    private Label price;

    @FXML
    private Label size;

    @FXML
    private Label state;

    @FXML
    private Label type;

    public void setData(Model data) {
	if (!(data instanceof Room)) {
		throw new RuntimeException("Incorrect data passed to RoomButton");
	}
	
	Room room = (Room)data;
	
        id.setText("Chambre " + room.getId());
        floor.setText("Etage " + room.getFloor());
	type.setText(room.getTypeString());
        price.setText(room.getPrice() + " Dt");
        size.setText(room.getCapacity() + " Personnes");
        state.setText(room.getStateString());
    }
}

