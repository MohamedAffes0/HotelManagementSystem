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

	public abstract void update(Model newData);

	protected abstract void dataFromFields();

	public void setData(Model data) {
		this.data = data;
	}

	public Model getData() {
		return data;
	}

	@Override
	// Called when the update button is pressed.
	public void suggestedPressed(ActionEvent event) {
		dataFromFields();
		update(getData());
	}

	@Override
	// Called when the delete button is pressed.
	public void destructivePressed(ActionEvent event) {

	}
}
