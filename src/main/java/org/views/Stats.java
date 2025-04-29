package org.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controllers.Controller;
import org.models.Model;
import org.models.Person;
import org.models.Room;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Stats implements Initializable {
	private final String LIST_BUTTON_PATH = "/listButton.fxml";

	@FXML
	private VBox mostCovetedRooms;

	@FXML
	private VBox faithfulClients;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ArrayList<Person> clients = Controller.getInstance().getClientManager().getFaithfulClients();
			updateList(faithfulClients, clients);

			ArrayList<Room> rooms = Controller.getInstance().getRoomManager().getMostCovetedRooms();
			updateList(mostCovetedRooms, rooms);
		} catch (Exception exception) {
		}
	}

	private void updateList(VBox list, ArrayList<? extends Model> data) throws Exception {
		if (list == null) {
			throw new Exception("Null list object");
		}

		if (data == null) {
			throw new Exception("Content not loaded from db");
		}

		list.getChildren().clear();

		if (data.size() == 0) {
			return;
		}

		for (Model model : data) {
			try {
				FXMLLoader listButtonLoader = new FXMLLoader(getClass().getResource(LIST_BUTTON_PATH));
				list.getChildren().add(listButtonLoader.load());
				ListButton controller = listButtonLoader.getController();
				controller.setData(model);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		// Set the style for the first and last buttons
		ListButton.updateStyle((Button) list.getChildren().get(0));
		ListButton.updateStyle((Button) list.getChildren().get(list.getChildren().size() - 1));
	}

}
