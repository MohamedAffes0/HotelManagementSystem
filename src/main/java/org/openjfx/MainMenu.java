package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.app.reservation.ReservationFilter;
import org.models.ReservationModel;
import org.models.RoomModel;

public class MainMenu implements Initializable {
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
	void chambresPressed(ActionEvent event) throws Exception {
		ListScreen<RoomModel> controller = new ListScreen<RoomModel>();
		changeCurrentMenu(controller,
				"Chambres",
				FXCollections.observableArrayList("Type", "test2"),
				"Ajouter Chambre");
	}

	@FXML
	void clientsPressed(ActionEvent event) throws Exception {
	}

	@FXML
	void comptesPressed(ActionEvent event) {

	}

	@FXML
	void reservationsPressed(ActionEvent event) {
		ListScreen<ReservationModel> controller = new ListScreen<ReservationModel>();
		changeCurrentMenu(controller,
				"Reservations",
				FXCollections.observableArrayList("test1", "test2"),
				"Ajouter Reservation");

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reservationsPressed(null);
		} catch (Exception e) {
			System.out.println(e);
		}
		main = new App();
	}

	private void changeCurrentMenu(ListScreen<?> controller, String title, ObservableList<String> filterItems,
			String addButtonText) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/listScreen.fxml"));

		try {
			// Set the controller
			loader.setController(controller);

			// Load menu
			Parent menu = loader.load();

			// Delete the previous menu and set this one
			contentContainer.getChildren().clear();
			contentContainer.getChildren().add(menu);

			// Anchor it correctly so that it displays over the entire screen.
			AnchorPane.setTopAnchor(menu, 0.0);
			AnchorPane.setBottomAnchor(menu, 0.0);
			AnchorPane.setLeftAnchor(menu, 0.0);
			AnchorPane.setRightAnchor(menu, 0.0);

			// Set Controller parameters
			controller.setTitle(title);
			controller.setFilterItems(filterItems);
			controller.setAddButtonText(addButtonText);

		} catch (IOException e) {
			System.out.println("Error loading screen");
		}
	}
	/*
	 * private void changeCurrentMenu(String menuPath) throws Exception {
	 * Parent menu = FXMLLoader.load(getClass().getResource(menuPath));
	 * 
	 * contentContainer.getChildren().clear();
	 * contentContainer.getChildren().add(menu);
	 * 
	 * AnchorPane.setTopAnchor(menu, 0.0);
	 * AnchorPane.setBottomAnchor(menu, 0.0);
	 * AnchorPane.setLeftAnchor(menu, 0.0);
	 * AnchorPane.setRightAnchor(menu, 0.0);
	 * }
	 */
}
