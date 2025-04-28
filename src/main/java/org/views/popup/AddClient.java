package org.views.popup;

import org.controllers.client.ClientAdd.CreationStatus;
import org.models.Model;
import org.models.Person;
import org.views.popupfield.NumberPopupField;
import org.views.popupfield.TextPopupField;
import org.controllers.client.ClientAdd;
import org.controllers.client.ClientSelect;

import javafx.stage.Stage;

public class AddClient extends AddPopup {
	final int CIN = 0;
	final int NAME = 1;
	final int LASTNAME = 2;
	final int EMAIL = 3;

	public AddClient() {
		super(
				new NumberPopupField("CIN"),
				new TextPopupField("Nom"),
				new TextPopupField("Prénom"),
				new TextPopupField("Email"));

		setTitle("Ajouter un client");
	}

	@Override
	public Person dataFromFields() {
		return new Person(
				(int) getField(CIN).getValue(),
				(String) getField(NAME).getValue(),
				(String) getField(LASTNAME).getValue(),
				(String) getField(EMAIL).getValue());

	}

	@Override
	protected void addData(Model newData) {
		CreationStatus result = ClientAdd.clientAdd((Person) newData, ClientSelect.dataFromDB());

		switch (result) {
			case SUCCESS:
				// Close the window after adding the room
				Stage stage = (Stage) getWindow();
				stage.close();
				break;
			case DB_PROBLEM:
				System.out.println("Database problem");
				break;
			case CIN_EXISTS:
				System.out.println("Exists");
				break;
		}
	}

}
