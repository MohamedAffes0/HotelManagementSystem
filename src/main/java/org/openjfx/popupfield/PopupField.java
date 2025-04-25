package org.openjfx.popupfield;

import java.io.ObjectInputStream.GetField;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * PopupField
 */
public abstract class PopupField {
	protected HBox container = new HBox();
	private Control field;
	private Label name = new Label();
	
	PopupField(String name) {
		this.name.setText(name);
		this.name.setMaxHeight(500);
		container.getChildren().add(this.name);

		AnchorPane anchorPane = new AnchorPane();
		HBox.setHgrow(anchorPane, Priority.ALWAYS);
		container.getChildren().add(anchorPane);
	}
	
	public HBox getContainer() {
		return container;
	}

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getName() {
		return name.getText();
	}

	public void setField(Control field) {
		this.field = field;
		if (field != null) {
			this.field.setPrefWidth(200);
			container.getChildren().add(this.field);
		}
	}

	public Control getField() {
		return field;
	}

	public abstract Object getValue();

	public abstract void setValue(Object value);

	public abstract boolean isEmpty();
}
