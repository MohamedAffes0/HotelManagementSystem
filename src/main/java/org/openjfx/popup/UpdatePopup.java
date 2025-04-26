package org.openjfx.popup;

import org.models.Model;
import org.openjfx.popupfield.PopupField;
import java.util.ResourceBundle;
import java.net.URL;
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

	public abstract void delete();

	public abstract void fieldsFromData();

	protected abstract void dataFromFields();

	public void setData(Model data) {
		this.data = data;
	}

	public Model getData() {
		return data;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		fieldsFromData();
	}

	@Override
	// Called when the update button is pressed.
	public void suggestedPressed(ActionEvent event) {
		dataFromFields();
		close();
	}

	@Override
	// Called when the delete button is pressed.
	public void destructivePressed(ActionEvent event) {
		delete();
		data = null;
		close();
	}
}
