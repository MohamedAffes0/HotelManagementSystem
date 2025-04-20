package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

import org.models.Model;
import javafx.scene.control.Button;

public class ListButton {

	@FXML
	public Button button;

	@FXML
	private HBox content;

	@FXML
	private Label id;

	public void setData(Model data) throws Exception {
		ArrayList<String> list = data.getStringData();

		if (list == null || list.isEmpty()) {
			throw new Exception("No data in model");
		}

		id.setText(list.get(0));

		for (int i = 1; i < list.size(); i++) {
			Label label = new Label(list.get(i));
			content.getChildren().add(label);
		}
	}

	@FXML
	void pressed(ActionEvent event) {
		System.out.println("pressed");
	}
}
