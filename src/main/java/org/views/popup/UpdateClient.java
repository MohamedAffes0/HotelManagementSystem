package org.views.popup;

import org.app.client.ClientDelete;
import org.app.client.ClientModify;
import org.app.user.ControllerException;
import org.models.Model;
import org.models.Person;
import org.views.popupfield.TextPopupField;

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
			throw new RuntimeException("Invalid data received");
		Person person = (Person) newData;

		ClientModify.clientModify(person);
	}

	@Override
	public void delete() {
		ClientDelete.clientDelete(((Person) getData()).getCin());
	}

	@Override
	public void fieldsFromData() {
		Person person = (Person) getData();
		((TextPopupField) getField(NAME)).setValue(person.getName());
		((TextPopupField) getField(LASTNAME)).setValue(person.getLastName());
		((TextPopupField) getField(MAIL)).setValue(person.getMail());
	}
}
