package org.models;

import java.util.ArrayList;

import org.controllers.StringNumberExtract;
import org.controllers.client.ClientFilter;

import javafx.scene.control.TextField;

public class Person extends Model {
	private int cin;
	private String name;
	private String lastName;
	private String mail;

	public Person(int cin, String name, String lastName, String mail) {
		this.cin = cin;
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
	}

	@Override
	public int getId() {
		return cin;
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
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		String clientId = "";
		int numberOfZeros = 8 - String.valueOf(getCin()).length();
		for (int i = 0; i < numberOfZeros; i++) {
			clientId += "0";
		}
		clientId = clientId + String.valueOf(getCin());
		data.add(new ModelField(clientId, null, createIcon(
				"M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm9 1.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 0-1h-4a.5.5 0 0 0-.5.5M9 8a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 0-1h-4A.5.5 0 0 0 9 8m1 2.5a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 0-1h-3a.5.5 0 0 0-.5.5m-1 2C9 10.567 7.21 9 5 9c-2.086 0-3.8 1.398-3.984 3.181A1 1 0 0 0 2 13h6.96q.04-.245.04-.5M7 6a2 2 0 1 0-4 0 2 2 0 0 0 4 0",
				0.9, "#ffffff")));
		data.add(new ModelField(getName(), "name-field"));
		data.add(new ModelField(getLastName(), "last-name-field"));
		data.add(new ModelField(getMail(), "mail-field-user", createIcon(
				"M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414zM0 4.697v7.104l5.803-3.558zM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586zm3.436-.586L16 11.801V4.697z",
				0.9, "#ffffff")));

		return data;
	}
}
