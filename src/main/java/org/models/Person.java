package org.models;

import java.util.ArrayList;

import org.app.StringNumberExtract;
import org.app.client.ClientFilter;

import javafx.scene.control.TextField;

public class Person extends Model{
    private int cin;
    private String name;
    private String lastName;
    private String mail;

    public Person(int cin, String name, String lastName, String mail) {

        if (cin <= 0) {
            throw new IllegalArgumentException("L'ID doit être supérieur à 0.");
        }

        this.cin = cin;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
    }

    public int getCin() {
        return cin;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
	public boolean filter(TextField search, String filterType) {
		String searchText = search.getText();

		if (searchText.isEmpty()) {
			return true;
		}

		// String is not empty so check filter type
		switch (filterType) {
			case "Cin":
				search.setText(StringNumberExtract.extract(searchText));
				search.positionCaret(search.getText().length());
                return ClientFilter.filterByCin(this, search.getText());
			default:
				return true;
		}
	}

	@Override
	public ArrayList<String> getStringData() {
		ArrayList<String> data = new ArrayList<String>();

		data.add("Cin: " + getCin());
		data.add("Nom " + getName());
		data.add("Prenom " + getLastName());
		// data.add("Mail " + getMail());
		data.add(getMail());

		return data;
	}
}
