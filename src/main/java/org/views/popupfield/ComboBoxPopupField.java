package org.views.popupfield;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * ComboBoxPopupField
 */
public class ComboBoxPopupField extends PopupField<ComboBox<String>, String> {
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

	public void setValue(String value) {
		getField().setValue(value);
	}

	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}
}
