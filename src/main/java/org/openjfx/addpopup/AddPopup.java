package org.openjfx.addpopup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.openjfx.popupfield.PopupField;

/**
 * AddPupup
 */
public abstract class AddPopup implements Initializable {
	private ArrayList<PopupField> fields = new ArrayList<>();
	private String titleText;
	final String filePath = "/addPopup.fxml";

	@FXML
	private Label title;

	@FXML
	private Button cancelButton;

	@FXML
	private Button addButton;

	@FXML
	private VBox container;

	public AddPopup() {
	}

	public AddPopup(ArrayList<PopupField> fields) {
		this.fields = fields;
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) title.getScene().getWindow();
		stage.close();
	}

	@FXML
	public abstract void addPressed(ActionEvent event);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (PopupField field : fields) {
			container.getChildren().add(field.getContainer());
		}
		title.setText(titleText);
	}

	public void setTitle(String newTitle) {
		titleText = newTitle;
		if (title != null) {
			title.setText(newTitle);
		}
	}

	public void setFields(PopupField... fields) {
		this.fields = new ArrayList<PopupField>(Arrays.asList(fields));
	}

	public PopupField getField(int index) {
		return fields.get(index);
	}

	public Parent load() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		// Set self as a controller
		loader.setController(this);

		return loader.load();
	}

	public Window getWindow() {
		if (title == null) {
			return null;
		}

		return title.getScene().getWindow();
	}
}
