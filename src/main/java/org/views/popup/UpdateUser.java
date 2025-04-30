package org.views.popup;

import org.controllers.Controller;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Employee;
import org.models.Model;
import org.views.popupfield.ComboBoxPopupField;

import javafx.collections.FXCollections;

public class UpdateUser extends UpdatePopup {
	final int TYPE = 0;
	final int STATUS = 1;

	public UpdateUser() {
		// Champs de saisie pour l'utilisateur
		super(
				new ComboBoxPopupField("Type",
						FXCollections.observableArrayList("Admin", "Employé")),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Actif", "Inactif")));

		setTitle("Modifier un utilisateur"); // Titre de la fenêtre contextuelle
	}

	@Override
	protected Employee dataFromFields() {

		Employee employee = (Employee) getData(); // Récupérer les données actuelles de l'utilisateur

		boolean isAdmin = false;
		switch ((String) getField(TYPE).getValue()) {
			case "Admin":
				isAdmin = true;
				break;
			case "Employé":
				isAdmin = false;
				break;
		}

		boolean isActive = false;
		switch ((String) getField(STATUS).getValue()) {
			case "Actif":
				isActive = true;
				break;
			case "Inactif":
				isActive = false;
				break;
		}

		return new Employee(employee.getId(), employee.getName(), employee.getName(), employee.getMail(),
				employee.getPassword(), isAdmin, isActive);
	}

	@Override
	public void update(Model newData) throws ControllerException{
		// Vérifier si les données reçues sont valides
		if (!(newData instanceof Employee))
			throw new ControllerException("Invalid data received");

		// Mettre à jour l'utilisateur dans la base de données
		Controller.getInstance().getUserManager().update(getData().getId(), (Employee) newData);
	}

	@Override
	public void delete() {
		try {
			// Supprimer l'utilisateur de la base de données
			Controller.getInstance().getUserManager().delete(getData().getId());
		} catch (DBException exception) {
			// Afficher le message d'erreur dans la fenêtre contextuelle
			setErrorMessage(exception.toString());
		}
	}

	@Override
	public void fieldsFromData() {
		Employee employee = (Employee) getData(); // Récupérer les données de l'utilisateur

		if (employee.isAdmin()) {
			((ComboBoxPopupField) getField(TYPE)).setValue("Admin");
		} else {
			((ComboBoxPopupField) getField(TYPE)).setValue("Employé");
		}

		if (employee.isActive()) {
			((ComboBoxPopupField) getField(STATUS)).setValue("Actif");
		} else {
			((ComboBoxPopupField) getField(STATUS)).setValue("Inactif");
		}
	}
}
