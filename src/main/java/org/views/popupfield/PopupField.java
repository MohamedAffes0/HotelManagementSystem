package org.views.popupfield;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Base class for add and update popups.
 * 
 * @param <T> The type of the control that it contains
 * @param <V> The type of its value
 */
public abstract class PopupField<T extends Control, V> {
	protected HBox container = new HBox();
	private T field;
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

	public void setField(T field) {
		this.field = field;
		if (field != null) {
			this.field.setPrefWidth(200);
			container.getChildren().add(this.field);
		}
	}

	public T getField() {
		return field;
	}

	public abstract V getValue();

	public abstract void setValue(V value);

	public abstract boolean isEmpty();
}
