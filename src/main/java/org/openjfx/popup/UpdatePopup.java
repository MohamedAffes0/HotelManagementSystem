package org.openjfx.popup;

import org.models.Model;
import org.openjfx.popupfield.PopupField;

import javafx.event.ActionEvent;

/**
 * UpdatePopup
 */
abstract class UpdatePopup extends Popup {
	private int id = 0;

	public UpdatePopup(int id, PopupField... fields) {
		super();
		setFields(fields);

		this.id = id;
		setSuggestedText("Modifier");
		setDestructiveText("Supprimer");
		setCancelText("Annuler");
	}

	public abstract Model getData();
	public abstract void update(Model newData);
	
	public int getId() {
		return id;
	}

	@Override
	// Called when the update button is pressed.
	public void suggestedPressed(ActionEvent event) {
		update(getData());
	}

	@Override
	// Called when the delete button is pressed.
	public void destructivePressed(ActionEvent event) {

	}
}
