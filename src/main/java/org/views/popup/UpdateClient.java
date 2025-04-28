package org.views.popup;

import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Model;
import org.models.Person;
import org.views.popupfield.TextPopupField;
import org.controllers.Controller;

public class UpdateClient extends UpdatePopup {
	final int NAME = 0;
	final int LASTNAME = 1;
	final int MAIL = 2;

	public UpdateClient() {
		super(
				new TextPopupField("Nom"),
				new TextPopupField("Prénom"),
				new TextPopupField("Email"));

		setTitle("Modifier un client");
	}

	@Override
	protected Person dataFromFields() {
		Person currentData = (Person) getData();

		String name = (String) getField(NAME).getValue();
		String lastName = (String) getField(LASTNAME).getValue();
		String mail = (String) getField(MAIL).getValue();

		return new Person(currentData.getCin(), name, lastName, mail);
	}

	@Override
	public void update(Model newData) throws ControllerException {
		if (!(newData instanceof Person))
			throw new ControllerException("Invalid data received");

		Controller.getInstance().getClientManager().update(getData().getId(), (Person) newData);
	}

	@Override
	public void delete() {
		try {
			Controller.getInstance().getClientManager().delete(getData().getId());
		} catch (DBException exception) {
			setErrorMessage(exception.toString());
		}
	}

	@Override
	public void fieldsFromData() {
		Person person = (Person) getData();
		((TextPopupField) getField(NAME)).setValue(person.getName());
		((TextPopupField) getField(LASTNAME)).setValue(person.getLastName());
		((TextPopupField) getField(MAIL)).setValue(person.getMail());
	}
}
