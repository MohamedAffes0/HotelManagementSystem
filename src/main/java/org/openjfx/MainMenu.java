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
import org.openjfx.popup.AddClient;
import org.openjfx.popup.AddReservation;
import org.openjfx.popup.AddRoom;
import org.openjfx.popup.AddUser;
import org.openjfx.popup.UpdateClient;
import org.openjfx.popup.UpdateReservation;
import org.openjfx.popup.UpdateRoom;
import org.openjfx.popup.UpdateUser;

public class MainMenu implements Initializable {
	private App main;

	@FXML
	private Button stats;

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
	private Button logout;

	@FXML
	void statsPressed(ActionEvent event) throws Exception {

		Parent menu = FXMLLoader.load(getClass().getResource("/stats.fxml"));

		// Delete the previous menu and set this one
		contentContainer.getChildren().clear();
		contentContainer.getChildren().add(menu);

		// Anchor it correctly so that it displays over the entire screen.
		AnchorPane.setTopAnchor(menu, 0.0);
		AnchorPane.setBottomAnchor(menu, 0.0);
		AnchorPane.setLeftAnchor(menu, 0.0);
		AnchorPane.setRightAnchor(menu, 0.0);
	}

	@FXML
	void chambresPressed(ActionEvent event) throws Exception {
		ListScreen<Room, RoomSelect> controller = new ListScreen<Room, RoomSelect>(new RoomSelect(),
				new AddRoom(), new UpdateRoom());
		changeCurrentMenu(controller,
				"Chambres",
				FXCollections.observableArrayList("Etage", "Type", "Capacité", "Prix", "Etat"),
				"Ajouter Chambre");
	}

	@FXML
	void clientsPressed(ActionEvent event) throws Exception {
		ListScreen<Person, ClientSelect> controller = new ListScreen<Person, ClientSelect>(new ClientSelect(),
				new AddClient(), new UpdateClient());
		changeCurrentMenu(controller,
				"Clients",
				FXCollections.observableArrayList("Cin"),
				"Ajouter Client");
	}

	@FXML
	void comptesPressed(ActionEvent event) {
		ListScreen<Employee, UserSelect> controller = new ListScreen<Employee, UserSelect>(new UserSelect(),
				new AddUser(), new UpdateUser());
		changeCurrentMenu(controller,
				"Comptes",
				FXCollections.observableArrayList(),
				"Ajouter Compte");
	}

	@FXML
	void reservationsPressed(ActionEvent event) {
		ListScreen<Reservation, ReservationSelect> controller = new ListScreen<Reservation, ReservationSelect>(
				new ReservationSelect(), new AddReservation(), new UpdateReservation());
		changeCurrentMenu(controller,
				"Réservations",
				FXCollections.observableArrayList("Est Payé", "Client", "Chambre"),
				"Ajouter Réservation");
	}

	@FXML
	void logOutPressed(ActionEvent event) throws Exception {
		try {
			main.changeScene("/login.fxml");
			System.out.println("Logged out successfully!");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reservationsPressed(null);
		} catch (Exception e) {
			System.out.println(e);
		}
		// Set the icon for the "stats" button
		String svgPath = "M4 11H2v3h2zm5-4H7v7h2zm5-5v12h-2V2zm-2-1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h2a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM6 7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1zm-5 4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1z";
		UiUtils.setIconToButton(stats, svgPath, 0.8, "#ffffff");

		// Set the icon for the "comptes" button
		svgPath = "M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1zm-7.978-1L7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002-.014.002zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0M6.936 9.28a6 6 0 0 0-1.23-.247A7 7 0 0 0 5 9c-4 0-5 3-5 4q0 1 1 1h4.216A2.24 2.24 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816M4.92 10A5.5 5.5 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0m3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4";
		UiUtils.setIconToButton(comptes, svgPath, 0.8, "#ffffff");

		// Set the icon for the "clients" button
		svgPath = "M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m.256 7a4.5 4.5 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10q.39 0 .74.025c.226-.341.496-.65.804-.918Q8.844 9.002 8 9c-5 0-6 3-6 4s1 1 1 1zm3.63-4.54c.18-.613 1.048-.613 1.229 0l.043.148a.64.64 0 0 0 .921.382l.136-.074c.561-.306 1.175.308.87.869l-.075.136a.64.64 0 0 0 .382.92l.149.045c.612.18.612 1.048 0 1.229l-.15.043a.64.64 0 0 0-.38.921l.074.136c.305.561-.309 1.175-.87.87l-.136-.075a.64.64 0 0 0-.92.382l-.045.149c-.18.612-1.048.612-1.229 0l-.043-.15a.64.64 0 0 0-.921-.38l-.136.074c-.561.305-1.175-.309-.87-.87l.075-.136a.64.64 0 0 0-.382-.92l-.148-.045c-.613-.18-.613-1.048 0-1.229l.148-.043a.64.64 0 0 0 .382-.921l-.074-.136c-.306-.561.308-1.175.869-.87l.136.075a.64.64 0 0 0 .92-.382zM14 12.5a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0";
		UiUtils.setIconToButton(clients, svgPath, 0.8, "#ffffff");

		// Set the icon for the "chambres" button
		svgPath = "M2.6803 18.3257H3.14529C3.55161 18.3257 3.81701 18.0681 3.81701 17.6618V16.4836C3.90822 16.5087 4.17397 16.5251 4.36461 16.5251H19.6733C19.8643 16.5251 20.1218 16.5083 20.213 16.4836V17.6618C20.213 18.0685 20.4784 18.3257 20.8851 18.3257H21.3579C21.7646 18.3257 22.03 18.0681 22.03 17.6618V12.2519C22.03 10.833 21.3082 10.0365 19.9723 9.93668V7.2151C19.9723 5.74648 19.1925 5 17.7654 5H6.26496C4.84605 5 4.05808 5.74648 4.05808 7.2151V9.93704C2.72144 10.0361 2 10.8327 2 12.2516V17.6615C2 18.0681 2.2654 18.3257 2.6803 18.3257ZM6.14049 8.45947C6.14049 7.72945 6.54681 7.32276 7.29364 7.32276H10.0735C10.8118 7.32276 11.2267 7.72945 11.2267 8.45947V9.9281H6.14049V8.45947ZM12.8116 8.45947C12.8116 7.72945 13.2182 7.32276 13.9651 7.32276H16.7364C17.4828 7.32276 17.8981 7.72945 17.8981 8.45947V9.9281H12.8119L12.8116 8.45947ZM3.65963 15.3137C3.49367 15.3137 3.38565 15.1974 3.38565 15.0064V12.1271C3.38565 11.5216 3.7841 11.1399 4.39788 11.1399H19.6407C20.2463 11.1399 20.6447 11.5216 20.6447 12.1271V15.0064C20.6447 15.1974 20.5367 15.3137 20.3793 15.3137H3.65963Z";
		UiUtils.setIconToButton(chambres, svgPath, 0.7, "#ffffff");

		// Set the icon for the "reservations" button
		svgPath = "M14 4v-.994C14 2.45 13.55 2 12.994 2H11v1h-1V2H6v1H5V2H3.006C2.45 2 2 2.45 2 3.006v9.988C2 13.55 2.45 14 3.006 14h9.988C13.55 14 14 13.55 14 12.994V5H2V4zm-3-3h1.994C14.102 1 15 1.897 15 3.006v9.988A2.005 2.005 0 0 1 12.994 15H3.006A2.005 2.005 0 0 1 1 12.994V3.006C1 1.898 1.897 1 3.006 1H5V0h1v1h4V0h1zM4 7h2v1H4zm3 0h2v1H7zm3 0h2v1h-2zM4 9h2v1H4zm3 0h2v1H7zm3 0h2v1h-2zm-6 2h2v1H4zm3 0h2v1H7zm3 0h2v1h-2z";
		UiUtils.setIconToButton(reservations, svgPath, 0.8, "#ffffff");

		// Set the icon for the "logout" button
		svgPath = "M11 4.25a7.75 7.75 0 1 0 5.424 13.286a.75.75 0 1 0-1.05-1.072a6.25 6.25 0 1 1 0-8.929a.75.75 0 1 0 1.05-1.07A7.73 7.73 0 0 0 11 4.25"
				+ "M12.53 9.53a.75.75 0 0 0-1.06-1.06l-3 3a.75.75 0 0 0 0 1.06l3 3a.75.75 0 1 0 1.06-1.06l-1.72-1.72H20a.75.75 0 0 0 0-1.5h-9.19z";
		UiUtils.setIconToButton(logout, svgPath, 0.8, "#F38BA8");

		main = new App();
	}

	private void changeCurrentMenu(ListScreen<?, ?> controller, String title, ObservableList<String> filterItems,
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
