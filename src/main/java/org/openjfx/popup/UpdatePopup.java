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

	// Updates the data in the database and returns true if it was changed
	// successfully.
	public abstract void update(Model newData) throws Exception;

	public abstract void delete();

	public abstract void fieldsFromData();

	protected abstract Model dataFromFields();

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
		try {
			Model data = dataFromFields();
			
			// Update can throw an exception
			update(data);

			// If update is successful then we set the data
			setData(data);
			
			// Close the window only if we succeded
			close();
		} catch (Exception exception) {
			// TODO: change this exception to a new more relevent class like
			// ControllerException
			setErrorMessage(exception.toString());
		}
	}

	@Override
	// Called when the delete button is pressed.
	public void destructivePressed(ActionEvent event) {
		delete();
		data = null;
		close();
	}
}
