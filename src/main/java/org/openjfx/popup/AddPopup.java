package org.openjfx.popup;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import org.models.Model;
import org.openjfx.popupfield.PopupField;

/**
 * AddPupup
 */
public abstract class AddPopup extends Popup {

	public AddPopup(PopupField... fields) {
		super();
		setFields(fields);

		setSuggestedText("Confirmer");
		setCancelText("Annuler");
	}

	protected abstract void addData(Model newData) throws Exception;

	protected abstract Model dataFromFields();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		try {
			removeDestructive();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	// Called when the update button is pressed.
	public void suggestedPressed(ActionEvent event) {
		try {
			addData(dataFromFields());
			close();
		} catch (Exception exception) {
			setErrorMessage(exception.toString());
		}
	}
}
