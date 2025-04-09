package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.collections.FXCollections;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Chambres implements Initializable{

    @FXML
    private ComboBox<String> filter;

    @FXML
    private VBox list;

    @FXML
    private TextField search;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	filter.setItems(FXCollections.observableArrayList("Etage", "Type", "Nombre Personnes", "Prix", "Etat"));
    }
    @FXML
    private void updateList() {
	System.out.println(filter.getValue());
    }

}
