package org.openjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

		content.getChildren().clear();
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
					if (popup.getData() == null) {
                        VBox parent = (VBox) button.getParent();
                        int index = getIndex();

						if (parent.getChildren().size() == 1) {
							parent.getChildren().remove(parent.getChildren().indexOf(button));
							return;
						}
                        if (isFirst()) {
                            parent.getChildren().get(index +1).setStyle("-fx-background-radius: 12 12 0 0;");
                        }
                        if (isLast()) {
                            parent.getChildren().get(index -1).setStyle("-fx-background-radius: 0 0 12 12;") ;
                        }
						if (parent.getChildren().size() == 2) {
							if (isFirst()) {
								parent.getChildren().get(index +1).setStyle("-fx-background-radius: 12;") ;
							} else {
								parent.getChildren().get(index -1).setStyle("-fx-background-radius: 12;") ;
							}
						}
						parent.getChildren().remove(parent.getChildren().indexOf(button));
                    } else {
                        setData(popup.getData());
                    }
				}
			});
			stage.show();
		}catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Error opening add popup");
		}
	}

	public void setStyle() {
		if (this.isFirst()) {
			button.setStyle("-fx-background-radius: 12 12 0 0;");
		}
		if (this.isLast()) {
			button.setStyle("-fx-background-radius: 0 0 12 12;");
		}
		if (this.isFirst() && this.isLast()) {
			button.setStyle("-fx-background-radius: 12;");
		}
		if (!this.isFirst() && !this.isLast()) {
			button.setStyle("-fx-background-radius: 0;");
		}
	}
}
