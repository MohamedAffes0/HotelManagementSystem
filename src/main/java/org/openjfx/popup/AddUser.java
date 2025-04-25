package org.openjfx.popup;

import org.app.user.UserAdd.CreationStatus;
import org.app.user.UserSelect;
import org.app.user.UserAdd;
import org.models.Employee;
import org.openjfx.popupfield.ComboBoxPopupField;
import org.openjfx.popupfield.TextPopupField;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AddUser extends AddPopup {
    final int NAME = 0;
	final int LASTNAME = 1;
	final int EMAIL = 2;
	final int PASSWORD = 3;
	final int TYPE = 4;
	final int STATUS = 5;


	public AddUser() {
		super();
		// Fill the fields for the room
		setFields(
				new TextPopupField("Nom"),
				new TextPopupField("Prénom"),
				new TextPopupField("Email"),
				new TextPopupField("Mot de passe"),
				new ComboBoxPopupField("Type",
						FXCollections.observableArrayList("Admin", "Employé")),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Actif", "Inactif"))
		);

		setTitle("Ajouter un utilisateur");
	}

	@Override
	public void addPressed(ActionEvent event) {
        Employee employee = new Employee(
                1,
                (String)getField(NAME).getValue(),
                (String)getField(LASTNAME).getValue(),
                (String)getField(EMAIL).getValue(),
                (String)getField(PASSWORD).getValue(),
                ((String)getField(TYPE).getValue()).equals("Admin"),
                ((String)getField(STATUS).getValue()).equals("Actif")
        );

        CreationStatus result = UserAdd.userAdd(employee, UserSelect.dataFromDB());

		switch (result) {
			case SUCCESS:
				// Close the window after adding the room
				Stage stage = (Stage) getWindow();
				stage.close();
				break;
			case CONNEXION_FAILED:
				break;
			case USER_EXISTS:
				System.out.println("Exists");
				break;
            case EMPTY_FIELD:
                System.out.println("Empty field");
                break;
		}
	}
}
