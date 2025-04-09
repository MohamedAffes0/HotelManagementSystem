package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;

public class MainMenu {
    private App main;

    @FXML
    private Button chambres;

    @FXML
    private Button clients;

    @FXML
    private Button comptes;

    @FXML
    private AnchorPane contentContainer;

    @FXML
    private Button reservations;

    @FXML
    void chambresPressed(ActionEvent event) throws Exception{
	changeCurrentMenu("/chambres.fxml");
    }

    @FXML
    void clientsPressed(ActionEvent event) {

    }

    @FXML
    void comptesPressed(ActionEvent event) {

    }

    @FXML
    void reservationsPressed(ActionEvent event) {

    }

    void MainMenu() throws Exception{
	main = new App();
    }
    
    private void changeCurrentMenu(String menuPath) throws Exception{
	Parent menu = FXMLLoader.load(getClass().getResource(menuPath));

    	contentContainer.getChildren().clear();
    	contentContainer.getChildren().add(menu);

    	AnchorPane.setTopAnchor(menu, 0.0);
    	AnchorPane.setBottomAnchor(menu, 0.0);
    	AnchorPane.setLeftAnchor(menu, 0.0);
    	AnchorPane.setRightAnchor(menu, 0.0);
    }
}

