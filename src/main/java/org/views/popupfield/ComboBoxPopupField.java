package org.views.popupfield;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * ComboBoxPopupField
 */
public class ComboBoxPopupField extends PopupField<ComboBox<String>, String> {

	public ComboBoxPopupField(String name) {
		super(name); // Le nom du champ qui sera utilisé dans la popup
		setField(new ComboBox<String>()); // Le champ qui sera utilisé dans la popup
	}

	public ComboBoxPopupField(String name, ObservableList<String> items) {
		super(name);
		ComboBox<String> comboBox = new ComboBox<>();
		setField(comboBox); // Le champ qui sera utilisé dans la popup

		comboBox.setItems(FXCollections.observableArrayList(items)); // Liste des éléments
		comboBox.setValue(items.get(0)); // Valeur par défaut
	}

	public void setItems(String... items) {
		ComboBox<String> comboBox = (ComboBox<String>) getField();
		comboBox.setItems(FXCollections.observableArrayList(items)); // Liste des éléments
		comboBox.setValue(items[0]); // Valeur par défaut
	}

	@Override
	/// Récupérer la valeur du champ
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
