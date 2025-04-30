package org.views;

import org.controllers.Manager;
import org.controllers.exceptions.DBException;
import org.models.Model;
import org.views.popup.AddPopup;
import org.views.popup.UpdatePopup;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListScreen<T extends Model> {
	private AddPopup addPopup;
	private UpdatePopup updatePopup;
	private Manager<T> manager;
	private final String LIST_BUTTON_PATH = "/listButton.fxml";

	@FXML
	private Button addButton;

	@FXML
	private ComboBox<String> filter;

	@FXML
	private VBox list;

	@FXML
	private HBox searchContainer;

	@FXML
	private TextField search;

	@FXML
	private Label title;

	public ListScreen(Manager<T> manager, AddPopup addPopup, UpdatePopup updatePopup) {
		try {
			manager.select();
		} catch (DBException exception) {
			System.out.println(exception);
		}
		this.manager = manager;
		this.addPopup = addPopup;
		this.updatePopup = updatePopup;
	}

	@FXML
	private void addPressed(ActionEvent event) {
		try {
			// Load the Popup
			addPopup.load("Ajout" + title.getText(), () -> {
				try {
					manager.select();
					updateList();
				} catch (Exception exception) {
					System.out.println(exception.getMessage());
				}
			});
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error opening add popup");
		}
	}

	@FXML
	protected void updateList() {
		if (manager.getData() == null)
			throw new RuntimeException("Content not loaded from db");

		list.getChildren().clear();

		if (manager.getData().size() == 0)
			return;

		for (T item : manager.getData()) {
			if (!manager.filter(item, filter.getValue(), search.getText()))
				continue;

			try {
				FXMLLoader listButtonLoader = new FXMLLoader(getClass().getResource(LIST_BUTTON_PATH));
				list.getChildren().add(listButtonLoader.load());
				ListButton controller = listButtonLoader.getController();
				controller.setData(item);
				controller.setPopup(updatePopup);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		if (list.getChildren().size() == 0)
			return;

		// Set the style for the first and last buttons
		ListButton.updateStyle((Button) list.getChildren().get(0));
		ListButton.updateStyle((Button) list.getChildren().get(list.getChildren().size() - 1));
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
		UiUtils.setIconToButton(addButton,
				"M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"
						+ "M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4",
				1, "#2f2f2f");
	}

	public void setAddPopup(AddPopup addPopup) {
		this.addPopup = addPopup;
	}

	public void setUpdatePopup(UpdatePopup updatePopup) {
		this.updatePopup = updatePopup;
	}
}
