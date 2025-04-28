package org.views.popupfield;


import org.controllers.StringFloatExtract;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FloatPopupField
 */
public class FloatPopupField extends PopupField<TextField, Float> {

	public FloatPopupField(String name) {
		super(name);
		TextField field = new TextField();
		setField(field);

		field.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String text = field.getText();
				field.setText(StringFloatExtract.extract(text));
				field.positionCaret(text.length());
			}
		});

	}

	public Float getValue() {
		if (isEmpty()) {
			return 0f;
		}
		return Float.parseFloat(getField().getText());
	}

	@Override
	public boolean isEmpty() {
		return getField().getText().isEmpty();
	}

	@Override
	public void setValue(Float value) {
		getField().setText(value.toString());
	}
}
