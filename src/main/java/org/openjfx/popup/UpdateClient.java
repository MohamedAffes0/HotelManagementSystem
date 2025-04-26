package org.openjfx.popup;

import org.app.client.ClientDelete;
import org.app.client.ClientModify;
import org.models.Employee;
import org.models.Model;
import org.models.Person;
import org.openjfx.popupfield.TextPopupField;


public class UpdateClient extends UpdatePopup {
    final int NAME = 0;
	final int LASTNAME = 1;
    final int MAIL = 2;

	public UpdateClient() {
		super(
                new TextPopupField("Nom"),
                new TextPopupField("Prénom"),
                new TextPopupField("Email")
        );

		setTitle("Modifier un client");
	}

	@Override
	protected void dataFromFields() {
        Person person = (Person) getData();
        person.setNom((String) getField(NAME).getValue());
        person.setPrenom((String) getField(LASTNAME).getValue());
        person.setMail((String) getField(MAIL).getValue());
	}

	@Override
	public void update(Model newData) {
		if (!(newData instanceof Person))
			throw new RuntimeException("Invalid data received");
		Person person = (Person) newData;

        ClientModify.clientModify(person);
	}

	@Override
	public void delete() {
        ClientDelete.clientDelete(((Person)getData()).getCin());
	}

	@Override
	public void fieldsFromData() {
        Person person = (Person) getData();
        ((TextPopupField) getField(NAME)).setValue(person.getName());
        ((TextPopupField) getField(LASTNAME)).setValue(person.getLastName());
        ((TextPopupField) getField(MAIL)).setValue(person.getMail());
	}
}
