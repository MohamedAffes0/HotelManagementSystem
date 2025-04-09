package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import javafx.collections.FXCollections;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.RoomSelect;
import java.util.ArrayList;
import org.models.RoomModel;


public class Chambres implements Initializable{
    private ArrayList<RoomModel> rooms;
    @FXML
    private ComboBox<String> filter;

    @FXML
    private VBox list;

    @FXML
    private TextField search;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	filter.setItems(FXCollections.observableArrayList("Etage", "Type", "Nombre Personnes", "Prix", "Etat"));
	
	list.getChildren().clear();

	try {
	    rooms = RoomSelect.roomSelect();
	
	    for (RoomModel room : rooms) {
	         System.out.println(room.getIdChambre());
	    }
	} catch (Exception e) {
	    System.out.println("Erreur de connection a la base de donnée");
	}
    }
    @FXML
    private void updateList() throws Exception{
	System.out.println(filter.getValue());
	Parent chambre = FXMLLoader.load(getClass().getResource("/chambreButton.fxml"));
	list.getChildren().clear();
	list.getChildren().add(chambre);
    }

}
