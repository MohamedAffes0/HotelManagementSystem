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
		super(name); // Le nom du champ qui sera utilisé dans la popup
		TextField field = new TextField();
		setField(field); // Le champ qui sera utilisé dans la popup

		field.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// Lorsque l'utilisateur tape dans le champ, on ne garde que les chiffres et le point
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
