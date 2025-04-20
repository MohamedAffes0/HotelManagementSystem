package org.openjfx;

import org.app.DBLoader;
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
import javafx.scene.control.Label; import javafx.scene.control.TextField; import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ListScreen<T extends Model, L extends DBLoader> {
	private String addPopupPath;
	private L loader;
	private	final String listButtonPath;

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

	public ListScreen(L loader) {
		listButtonPath = "/listButton.fxml";
		this.loader = loader;
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
						loadFromDB();
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
		if (loader.getData() == null) {
			throw new RuntimeException("Content not initialized");
		}

		list.getChildren().clear();
		for (Object o : loader.getData()) {
			T item = (T)o;
			if (!item.filter(search, filter.getValue())) {
				continue;
			}

			try {
				FXMLLoader listButtonLoader = new FXMLLoader(getClass().getResource(listButtonPath));
				list.getChildren().add(listButtonLoader.load());
				ListButton controller = listButtonLoader.getController();
				controller.setData(item);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	public void loadFromDB() {
		loader.load();
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

	public void setAddPopupPath(String path) {
		addPopupPath = path;
	}
}
