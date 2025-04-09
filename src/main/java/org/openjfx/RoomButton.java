package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.models.RoomModel;
import javafx.scene.control.Button;

public class RoomButton {

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

    public void setData(RoomModel data) {
	id.setText("Chambre " + Integer.toString(data.getIdChambre()));
	floor.setText("Etage " + Integer.toString(data.getEtage()));
	type.setText(data.getTypeChambre());
	price.setText(Float.toString(data.getPrix()) + " Dt");
	size.setText(Integer.toString(data.getNbPersonnes()) + " Personnes");
	
	switch (data.getEtat()) {
		case LIBRE:
			state.setText("Libre");
			break;
		case OCCUPEE:
			state.setText("Occupée");
			break;
		case MAINTENANCE:
			state.setText("Maintenance");
			break;
	}
    }
}

