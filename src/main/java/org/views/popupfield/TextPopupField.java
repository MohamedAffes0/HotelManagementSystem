package org.views.popupfield;

import javafx.scene.control.TextField;

/**
 * TextPopupField
 */
public class TextPopupField extends PopupField<TextField, String> {

	public TextPopupField(String name) {
		super(name);
		setField(new TextField());
	}

	@Override
	public String getValue() {
		return  getField().getText();
	}

	@Override
	public boolean isEmpty() {
		return getValue().isEmpty();
	}

	@Override
	public void setValue(String value) {
		getField().setText(value);
	}
}
