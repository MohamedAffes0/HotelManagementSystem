package org.openjfx.popup;

import org.app.client.ClientAdd.CreationStatus;
import org.models.Model;
import org.models.Person;
import org.openjfx.popupfield.NumberPopupField;
import org.openjfx.popupfield.TextPopupField;
import org.app.client.ClientAdd;
import org.app.client.ClientSelect;

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
