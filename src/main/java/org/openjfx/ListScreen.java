package org.openjfx;

import java.util.ArrayList;

import org.models.Model;

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

public class ListScreen<T extends Model> {
	private ArrayList<?> content;
	private String addPopupPath;
	private String updatePopupPath;
	private FXMLLoader listButtonLoader;

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

	public ListScreen() {
	}

	@FXML
	protected void addPressed(ActionEvent event) {
		try {
			Parent content = FXMLLoader.load(getClass().getResource(addPopupPath));
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
						// TODO add select base class
						// rooms = RoomSelect.roomSelect();
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
	protected void updateList() {
		if (content == null) {
			throw new RuntimeException("Content not initialized");
		}

		list.getChildren().clear();
		for (Object o : content) {
			T item = (T)o;
			if (!item.filter(search, filter.getValue())) {
				continue;
			}

			try {
				list.getChildren().add(listButtonLoader.load());
				ListButton controller = listButtonLoader.getController();
				controller.setData(item);
			} catch (Exception exception) {
				throw new RuntimeException("Failed to load list button");
			}
		}
	}

	public void loadFromDB() {
		content = T.select();
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

	public void setListButtonPath(String path) {
		listButtonLoader = new FXMLLoader(getClass().getResource(path));
	}

	public void setAddPopupPath(String path) {
		addPopupPath = path;
	}

	public void setUpdatePopupPath(String path) {
		updatePopupPath = path;
	}
}
