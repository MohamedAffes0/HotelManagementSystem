package org.openjfx.popup;

import org.models.Model;
import org.openjfx.popupfield.PopupField;

import javafx.event.ActionEvent;

/**
 * UpdatePopup
 */
public abstract class UpdatePopup extends Popup {
	private Model data;

	public UpdatePopup(PopupField... fields) {
		super();
		setFields(fields);

		setSuggestedText("Modifier");
		setDestructiveText("Supprimer");
		setCancelText("Annuler");
	}

	public abstract Model getData();
	public abstract void update(Model newData);
	

	public void setData(Model data) {
		this.data = data;
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
