package org.models;

import java.util.ArrayList;

import javafx.scene.control.TextField;

final public class Employee extends Person {
    private String password;
    private boolean isAdmin;
    private boolean isActive;

    public Employee(int id, String name, String lastName, String mail, String password, boolean isAdmin, boolean isActive) {
        super(id, name, lastName, mail);
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

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

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean filter(TextField search, String filterType) {
        return true;
    }

    
	@Override
	public ArrayList<String> getStringData() {
		ArrayList<String> data = new ArrayList<String>();

		data.add("Id " + getId());
		data.add("Nom " + getName());
		data.add("Prenom " + getLastName());
		data.add("Mail " + getMail());
        data.add("Mot de passe " + getPassword());
        data.add(isAdmin() ? "Admin" : "Receptionnel");
        data.add(isActive() ? "Actif" : "Inactif");

		return data;
	}
}
