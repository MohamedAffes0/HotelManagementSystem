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
			// Charger les données depuis la base de données et mettre à jour les listes
			ArrayList<Person> clients = Controller.getInstance().getClientManager().getFaithfulClients();
			updateList(faithfulClients, clients);

			ArrayList<Room> rooms = Controller.getInstance().getRoomManager().getMostCovetedRooms();
			updateList(mostCovetedRooms, rooms);
		} catch (Exception exception) {
		}
	}

	private void updateList(VBox list, ArrayList<? extends Model> data) throws Exception {

		// Vérifier si la liste et les données est vide
		if (list == null) {
			throw new Exception("Null list object");
		}

		// Vérifier si les données sont nulles
		if (data == null) {
			throw new Exception("Content not loaded from db");
		}

		//vider la liste avant de la remplir
		list.getChildren().clear();

		if (data.size() == 0) {
			return; // Si la liste est vide, ne pas ajouter de boutons
		}

		// Créer un bouton pour chaque élément de la liste et l'ajouter à la liste
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

		// Définir le style pour les premiers et derniers boutons
		ListButton.updateStyle((Button) list.getChildren().get(0));
		ListButton.updateStyle((Button) list.getChildren().get(list.getChildren().size() - 1));
	}

}
