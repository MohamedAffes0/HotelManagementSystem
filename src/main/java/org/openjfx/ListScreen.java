package org.openjfx;

import org.app.DBLoader;
import org.models.Model;
import org.openjfx.popup.AddPopup;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ListScreen<T extends Model, L extends DBLoader> {
	private AddPopup addPopup;
	private L loader;
	private final String LIST_BUTTON_PATH = "/listButton.fxml";

	@FXML
	protected Button addButton;

	@FXML
	protected ComboBox<String> filter;

	@FXML
	protected VBox list;

	@FXML
	private HBox searchContainer;

	@FXML
	protected TextField search;

	@FXML
	protected Label title;

	public ListScreen(L loader, AddPopup addPopup) {
		this.loader = loader;
		this.addPopup = addPopup;
	}

	@FXML
	protected void addPressed(ActionEvent event) {
		try {
			// Load the Popup
			// addPopup addRoom = new AddRoom();
			Parent content = addPopup.load();

			// Create the stage and configure it
			Stage stage = new Stage();
			Scene scene = new Scene(content);
			stage.setResizable(false);
			stage.setTitle("Ajout " + title.getText());
			stage.setScene(scene);
			// Reload list after closing the popup.
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						loadFromDB();
						updateList();
					} catch (Exception exception) {
						System.out.println("Erreur de connection a la base de donnée");
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error opening add popup");
		}
	}

	@FXML
	protected void updateList() {
		if (loader.getData() == null) {
			throw new RuntimeException("Content not loaded from db");
		}

		list.getChildren().clear();
		for (Object o : loader.getData()) {
			T item = (T) o;
			if (!item.filter(search, filter.getValue())) {
				continue;
			}

			try {
				FXMLLoader listButtonLoader = new FXMLLoader(getClass().getResource(LIST_BUTTON_PATH));
				list.getChildren().add(listButtonLoader.load());
				ListButton controller = listButtonLoader.getController();
				controller.setData(item);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		// Set the style for the first and last buttons
		Button firstButton = (Button) list.getChildren().get(0);
		firstButton.setStyle("-fx-background-radius: 12 12 0 0;");
		Button lastButton = (Button) list.getChildren().get(list.getChildren().size() - 1);
		lastButton.setStyle("-fx-background-radius: 0 0 12 12;");
		if (list.getChildren().size() == 1) {
			firstButton.setStyle("-fx-background-radius: 12;");
		}
	}

	public void loadFromDB() {
		loader.load();
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setFilterItems(ObservableList<String> items) {
		// Delete the search if no filter are provided.
		if (items.isEmpty()) {
			VBox mainContainer = (VBox) searchContainer.getParent();
			mainContainer.getChildren().remove(1);
			return;
		}
		filter.setItems(items);
		filter.setValue(items.get(0));
	}

	public void setAddButtonText(String text) {
		addButton.setText(text);
	}

	public void setAddPopup(AddPopup addPopup) {
		this.addPopup = addPopup;
	}
}
