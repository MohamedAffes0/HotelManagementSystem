package org.openjfx.popupfield;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * ComboBoxPopupField
 */
public class ComboBoxPopupField extends PopupField {
	// private ComboBox<String> field = new ComboBox<String>();

	public ComboBoxPopupField(String name) {
		super(name);
		setField(new ComboBox<String>());
	}

	public ComboBoxPopupField(String name, ObservableList<String> items) {
		super(name);
		ComboBox<String> comboBox = new ComboBox<>();
		setField(comboBox);

		comboBox.setItems(FXCollections.observableArrayList(items));
		comboBox.setValue(items.get(0));
	}

	public void setItems(String... items) {
		ComboBox<String> comboBox = (ComboBox<String>) getField();
		comboBox.setItems(FXCollections.observableArrayList(items));
		comboBox.setValue(items[0]);
	}

	@Override
	public String getValue() {
		return ((ComboBox<String>) getField()).getValue();
	}

	public void setValue(Object value) {
		((ComboBox<String>) getField()).setValue((String) value);
	}

	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}
}
