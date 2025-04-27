package org.views.popup;

import org.app.user.ControllerException;
import org.app.user.UserDelete;
import org.app.user.UserModify;
import org.models.Employee;
import org.models.Model;
import org.views.popupfield.ComboBoxPopupField;

import javafx.collections.FXCollections;

public class UpdateUser extends UpdatePopup {
	final int TYPE = 0;
	final int STATUS = 1;

	public UpdateUser() {
		super(
				new ComboBoxPopupField("Type",
						FXCollections.observableArrayList("Admin", "Employé")),
				new ComboBoxPopupField("Statut",
						FXCollections.observableArrayList("Actif", "Inactif")));

		setTitle("Modifier un utilisateur");
	}

	@Override
	protected Employee dataFromFields() {
		Employee employee = (Employee) getData();

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
		if (!(newData instanceof Employee))
			throw new RuntimeException("Invalid data received");
		Employee employee = (Employee) newData;

		UserModify.userModify(employee.getId(), employee.isAdmin(), employee.isActive());
	}

	@Override
	public void delete() {
		UserDelete.userDelete(((Employee) getData()).getId());
	}

	@Override
	public void fieldsFromData() {
		Employee employee = (Employee) getData();
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
