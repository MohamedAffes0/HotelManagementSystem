package org.openjfx.popupfield;

import javafx.scene.control.TextField;

/**
 * TextPopupField
 */
public class TextPopupField extends PopupField {
	// private TextField field = new TextField();

	public TextPopupField(String name) {
		super(name);
		setField(new TextField());
	}

	@Override
	public String getValue() {
		return ((TextField) getField()).getText();
	}

	@Override
	public boolean isEmpty() {
		return getValue().isEmpty();
	}

	@Override
	public void setValue(Object value) {
		((TextField) getField()).setText((String) value);
	}
}
