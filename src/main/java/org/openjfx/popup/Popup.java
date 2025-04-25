package org.openjfx.popup;

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
public abstract class Popup implements Initializable {
	private ArrayList<PopupField> fields = new ArrayList<>();
	private String titleText;
	private String suggestedText = "Suggested";
	private String destuctiveText = "Destructive";
	private String cancelText = "Cancel";
	final String filePath = "/addPopup.fxml";

	@FXML
	private Label title;

	@FXML
	// Container for all the fields in the popup.
	private VBox container;

	@FXML
	// Button that does the intended action of the popup. Like an add or an update.
	private Button suggestedButton;

	@FXML
	// A red button that does a destructive action like delete.
	private Button destructiveButton;

	@FXML
	// Closes the popup without doing anything.
	private Button cancelButton;

	public Popup() {
	}

	public Popup(ArrayList<PopupField> fields) {
		this.fields = fields;
	}

	@FXML
	// Called when the cancel button is pressed.
	public void cancelPressed(ActionEvent event) {
		Stage stage = (Stage) title.getScene().getWindow();
		stage.close();
	}

	@FXML
	// Called when the suggested button is pressed.
	public void suggestedPressed(ActionEvent event) {
	}

	@FXML
	public void destructivePressed(ActionEvent event) {
	}

	@Override
	// Called when the fxml file is fully loaded.
	public void initialize(URL location, ResourceBundle resources) {
		for (PopupField field : fields) {
			container.getChildren().add(field.getContainer());
		}
		title.setText(titleText);
		suggestedButton.setText(suggestedText);
		destructiveButton.setText(destuctiveText);
		cancelButton.setText(cancelText);
	}

	// Changes the title at the top of the popup.
	public void setTitle(String newTitle) {
		titleText = newTitle;
		if (title != null) {
			title.setText(newTitle);
		}
	}

	public void setSuggestedText(String text) {
		suggestedText = text;
		if (suggestedButton != null)
			suggestedButton.setText(text);
	}

	public void setDestructiveText(String text) {
		destuctiveText = text;
		if (destructiveButton != null)
			destructiveButton.setText(text);
	}

	public void setCancelText(String text) {
		cancelText = text;
		if (cancelButton != null)
			cancelButton.setText(text);
	}

	// Removes the destructive button from the popup.
	public void removeDestructive() throws Exception {
		if (destructiveButton == null) {
			throw new Exception("The destructive button is either uninitialized or removed");
		}
		VBox buttonVontainer = (VBox) destructiveButton.getParent();
		buttonVontainer.getChildren().remove(container.getChildren().indexOf(destructiveButton));
	}

	// Changes the fields in the popup.
	public void setFields(PopupField... fields) {
		this.fields = new ArrayList<PopupField>(Arrays.asList(fields));
	}

	// Retruns the field at the specified index.
	public PopupField getField(int index) {
		return fields.get(index);
	}

	// Loads the fxml file and returns its root.
	public Parent load() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		// Set self as a controller
		loader.setController(this);

		return loader.load();
	}

	// Returns the popup's current window if it's loaded, else returns null.
	public Window getWindow() {
		if (title == null) {
			return null;
		}

		return title.getScene().getWindow();
	}
}
