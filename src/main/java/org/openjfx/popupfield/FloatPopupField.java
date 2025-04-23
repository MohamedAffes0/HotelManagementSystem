package org.openjfx.popupfield;


import org.app.StringFloatExtract;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * NumberPopupField
 */
public class FloatPopupField extends PopupField {

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
		return Float.parseFloat(((TextField)getField()).getText());
	}

	@Override
	public boolean isEmpty() {
		return ((TextField)getField()).getText().isEmpty();
	}

}
