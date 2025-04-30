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
		super(name); // Le nom du champ qui sera utilisé dans la popup
		TextField field = new TextField(); 
		setField(field); // Le champ qui sera utilisé dans la popup

		field.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// Lorsque l'utilisateur tape dans le champ, on ne garde que les chiffres
				String text = field.getText();
				field.setText(StringNumberExtract.extract(text));
				field.positionCaret(text.length());
			}
		});

	}

	public void setOnAction(EventHandler<? super KeyEvent> event) {
		// Définit l'action à effectuer lorsque la valeur est modifiée
		getField().setOnKeyTyped(event);
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
