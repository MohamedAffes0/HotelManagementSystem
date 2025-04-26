package org.models;

import java.util.ArrayList;

import javafx.scene.control.TextField;

final public class Employee extends Person {
	private String password;
	private boolean isAdmin;
	private boolean isActive;

	public Employee(int id, String name, String lastName, String mail, String password, boolean isAdmin,
			boolean isActive) {
		super(id, name, lastName, mail);
		this.password = password;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
	}

	@Override
	public boolean filter(TextField search, String filterType) {
		return true;
	}

	@Override
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		data.add(new ModelField(getName() + " " + getLastName(), null));
		data.add(new ModelField(isAdmin() ? "Admin" : "Réceptionniste", "employee-type-badge"));
		String styleClass = "";
		if (isActive()) {
			styleClass = "active-user-badge";
		} else {
			styleClass = "inactive-user-badge";
		}
		data.add(new ModelField(getMail(), null));
		data.add(new ModelField(isActive() ? "Actif" : "Inactif", styleClass));

		return data;
	}

	// Getters
	public int getId() {
		return super.getCin();
	}

	public String getName() {
		return super.getName();
	}

	public String getLastName() {
		return super.getLastName();
	}

	public String getMail() {
		return super.getMail();
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public boolean isActive() {
		return isActive;
	}

	// Setters
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
