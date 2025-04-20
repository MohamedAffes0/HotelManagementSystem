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

import org.app.client.ClientSelect;
import org.app.reservation.ReservationSelect;
import org.app.room.RoomSelect;
import org.app.user.UserSelect;
import org.models.Employee;
import org.models.Person;
import org.models.Reservation;
import org.models.Room;

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
		ListScreen<Room, RoomSelect> controller = new ListScreen<Room, RoomSelect>(new RoomSelect());
		changeCurrentMenu(controller,
				"Chambres",
				FXCollections.observableArrayList("Etage", "Type", "Capacité", "Prix", "Etat"),
				"Ajouter Chambre",
				"/addRoom.fxml");
	}

	@FXML
	void clientsPressed(ActionEvent event) throws Exception {
		ListScreen<Person, ClientSelect> controller = new ListScreen<Person, ClientSelect>(new ClientSelect());
		changeCurrentMenu(controller,
				"Clients",
				FXCollections.observableArrayList("Cin"),
				"Ajouter Client",
				"/addRoom.fxml");
	}

	@FXML
	void comptesPressed(ActionEvent event) {
		ListScreen<Employee, UserSelect> controller = new ListScreen<Employee, UserSelect>(new UserSelect());
		changeCurrentMenu(controller,
				"Comptes",
				FXCollections.observableArrayList(),
				"Ajouter Compte",
				"/addRoom.fxml");
	}

	@FXML
	void reservationsPressed(ActionEvent event) {
		ListScreen<Reservation, ReservationSelect> controller = new ListScreen<Reservation, ReservationSelect>(new ReservationSelect());
		changeCurrentMenu(controller,
				"Réservations",
				FXCollections.observableArrayList("isPaid", "Client", "Chambre"),
				"Ajouter Réservation",
				"/addRoom.fxml");
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

	private void changeCurrentMenu(ListScreen<?, ?> controller, String title, ObservableList<String> filterItems,
			String addButtonText, String addPopupPath) {
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
			controller.setAddPopupPath(addPopupPath);
			controller.updateList();

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
