package org.openjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

import org.models.Model;
import org.openjfx.popup.UpdatePopup;

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

		ArrayList<String> list = data.getStringData();

		if (list == null || list.isEmpty())
			throw new RuntimeException("No data in model");

		id.setText(list.get(0));

		for (int i = 1; i < list.size(); i++) {
			Label label = new Label(list.get(i));
			label.setMinWidth(120);
			label.setAlignment(Pos.CENTER);
			content.getChildren().add(label);
		}
	}

	public void setPopup(UpdatePopup popup) {
		this.popup = popup;
	}

	@FXML
	void pressed(ActionEvent event) {
		try {
			// Load the Popup
			Parent content = popup.load();
			popup.setData(data);

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
					setData(popup.getData());
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error opening add popup");
		}
	}
}
