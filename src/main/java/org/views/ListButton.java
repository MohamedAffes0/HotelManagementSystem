package org.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

import org.models.Model;
import org.models.ModelField;
import org.views.popup.UpdatePopup;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ListButton {
	Model data;
	UpdatePopup popup;

	@FXML
	public Button button;

	@FXML
	private HBox content;

	@FXML
	private Label id;

	public void setData(Model data) {
		if (data == null)
			throw new RuntimeException("Null model");

		this.data = data;

		ArrayList<ModelField> fields = data.getFields();

		if (fields == null || fields.isEmpty())
			throw new RuntimeException("No data in model");

		id.setText(fields.get(0).getContent());
		if (fields.get(0).getIcon() != null) {
			id.setGraphic(fields.get(0).getIcon());
			id.setContentDisplay(fields.get(0).getIconPosition());
		}

		content.getChildren().clear();

		for (int i = 1; i < fields.size(); i++) {
			ModelField field = fields.get(i);
			Label label = new Label(field.getContent());

			// label.setAlignment(Pos.CENTER);
			if (field.getStyleClass() != null)
				label.getStyleClass().add(field.getStyleClass());
				label.setStyle("-fx-font-weight: bold;");

			if (field.getIcon() != null) {
				label.setGraphic(field.getIcon());
				label.setContentDisplay(field.getIconPosition());
			}

			content.getChildren().add(label);
		}
	}

	public void setPopup(UpdatePopup popup) {
		this.popup = popup;
	}

	public int getIndex() {
		VBox parent = (VBox) button.getParent();
		return parent.getChildren().indexOf(button);
	}

	// Returns true if the button is the first in the list.
	public boolean isFirst() {
		return getIndex() == 0;

	}

	public boolean isLast() {
		VBox parent = (VBox) button.getParent();
		return getIndex() == (parent.getChildren().size() - 1);
	}

	@FXML
	void pressed(ActionEvent event) {
		try {
			popup.setData(data);
			// Load the Popup
			Parent content = popup.load();

			// Create the stage and configure it
			Stage stage = new Stage();
			Scene scene = new Scene(content);
			stage.setResizable(false);
			// stage.setTitle("Ajout " + title.getText());
			stage.setScene(scene);
			// Reload list after closing the popup.
			stage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if (popup.getData() != null) {
						setData(popup.getData());
						return;
					}
					VBox parent = (VBox) button.getParent();
					int index = getIndex();

					// Delete the button
					parent.getChildren().remove(index);

					// Do nothing if the there are no buttons
					if (parent.getChildren().size() == 0) {
						return;
					}

					// Change the preceeding button if not first
					if (index > 0) {
						updateStyle((Button) parent.getChildren().get(index - 1));
						return;
					}

					// Change the following button if not last
					if (index < parent.getChildren().size()) {
						updateStyle((Button) parent.getChildren().get(index));
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error opening add popup");
		}
	}

	// Changes the border radius of the button if it's at the top or bottom of the
	// list.
	public static void updateStyle(Button button) {
		VBox parent = (VBox) button.getParent();
		int index = parent.getChildren().indexOf(button);
		int listSize = parent.getChildren().size();

		// There is only 1 button
		if (listSize == 1) {
			button.setStyle("-fx-background-radius: 12;");
			return;
		}

		// The button is at the top
		if (index == 0) {
			button.setStyle("-fx-background-radius: 12 12 0 0;");
			return;
		}

		// The button is at the bottom
		if (index == (listSize - 1)) {
			button.setStyle("-fx-background-radius: 0 0 12 12;");
			return;
		}

		// The button is in the middle (neither top nor bottom)
		button.setStyle("-fx-background-radius: 0;");
	}
}
