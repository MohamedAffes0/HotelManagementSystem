package org.views.popup;

import org.models.Employee;
import org.models.Model;
import org.views.popupfield.ComboBoxPopupField;
import org.views.popupfield.TextPopupField;
import org.controllers.Controller;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class AddUser extends AddPopup {
	final int NAME = 0;
	final int LASTNAME = 1;
	final int EMAIL = 2;
	final int PASSWORD = 3;
	final int TYPE = 4;
	final int STATUS = 5;

	public AddUser() {
		// Champs de saisie pour l'utilisateur
		super(
				new TextPopupField("Nom"),
				new TextPopupField("Prénom"),
				new TextPopupField("Email"),
				new TextPopupField("Mot de passe"),
				new ComboBoxPopupField("Type",
						FXCollections.observableArrayList("Admin", "Employé")),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Actif", "Inactif")));

		setTitle("Ajouter un utilisateur"); // Titre de la fenêtre contextuelle
	}

	@Override
	protected Model dataFromFields() {
		return new Employee(
				1,
				(String) getField(NAME).getValue(),
				(String) getField(LASTNAME).getValue(),
				(String) getField(EMAIL).getValue(),
				(String) getField(PASSWORD).getValue(),
				((String) getField(TYPE).getValue()).equals("Admin"),
				((String) getField(STATUS).getValue()).equals("Actif"));
	}

	@Override
	protected void addData(Model newData) throws Exception{
			Controller.getInstance().getUserManager().insert((Employee) newData); // Ajouter l'utilisateur à la base de données
			Stage stage = (Stage) getWindow(); // Récupérer la scène de la fenêtre contextuelle
			stage.close(); // Fermer la fenêtre contextuelle
	}

}
