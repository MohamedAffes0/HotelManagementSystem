package org.openjfx;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ListScreen<T> {
	private ArrayList<T> content;
	private String listButtonPath;
	private String addPopupPath;

	@FXML
	protected Button addButton;

	@FXML
	protected ComboBox<String> filter;

	@FXML
	protected VBox list;

	@FXML
	protected TextField search;

	@FXML
	protected Label title;

	@FXML
	protected void addPressed(ActionEvent event) {
		try {
			Parent content = FXMLLoader.load(getClass().getResource(addPopupPath));
			Stage stage = new Stage();
			Scene scene = new Scene(content);
			stage.setResizable(false);

			stage.setTitle("Ajout " + title.getText());
			stage.setScene(scene);
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						// TODO add select base class
						//rooms = RoomSelect.roomSelect();
						updateList();
					} catch (Exception exception) {
						System.out.println("Erreur de connection a la base de donnée");
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println("Error opening add room popup");
		}
	}

	@FXML
	protected void updateList() throws IOException {
		list.getChildren().clear();
		for (T item : content) {
			// TODO add filter condition
			FXMLLoader loader = new FXMLLoader(getClass().getResource(listButtonPath));

			list.getChildren().add(loader.load());
			ListButton controller = loader.getController();
			controller.setData(item);
		}
	}

	public ListScreen() {
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setFilterItems(ObservableList<String> items) {
		if (items.isEmpty()) {
			throw new RuntimeException("Items list for filter must not be empty.");
		}
		filter.setItems(items);
		filter.setValue(items.get(0));
	}

	public void setAddButtonText(String text) {
		addButton.setText(text);
	}
}
