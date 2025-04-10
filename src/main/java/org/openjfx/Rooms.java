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

//import org.openjfx.RoomButton;

import org.app.RoomSelect;
import java.util.ArrayList;
import org.models.RoomModel;


public class Rooms implements Initializable{
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
	    updateList();
	} catch (Exception e) {
	    System.out.println("Erreur de connection a la base de donnée");
	}
    }

    @FXML
    private void updateList() throws Exception{
	System.out.println(filter.getValue());
	
	rooms = RoomSelect.roomSelect();
	
	list.getChildren().clear();
	for (RoomModel data: rooms) {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/roomButton.fxml"));
	    list.getChildren().add(loader.load());
	    RoomButton controller = loader.getController();
	    controller.setData(data);
	}
    }

}
