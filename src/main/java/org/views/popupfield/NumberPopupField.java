package org.views.popupfield;

import org.controllers.StringNumberExtract;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * NumberPopupField
 */
public class NumberPopupField extends PopupField<TextField, Integer> {

	public NumberPopupField(String name) {
		super(name);
		TextField field = new TextField();
		setField(field);

		field.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String text = field.getText();
				field.setText(StringNumberExtract.extract(text));
				field.positionCaret(text.length());
			}
		});

	}

	public Integer getValue() {
		if (isEmpty()) {
			return 0;
		}
		return Integer.parseInt(getField().getText());
	}

	@Override
	public boolean isEmpty() {
		return getField().getText().isEmpty();
	}

	@Override
	public void setValue(Integer value) {
		getField().setText(value.toString());
	}

}
