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
	public ArrayList<ModelField> getFields() {
		ArrayList<ModelField> data = new ArrayList<>();

		data.add(new ModelField(String.valueOf(getCin()), null, createIcon("M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm9 1.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 0-1h-4a.5.5 0 0 0-.5.5M9 8a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 0-1h-4A.5.5 0 0 0 9 8m1 2.5a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 0-1h-3a.5.5 0 0 0-.5.5m-1 2C9 10.567 7.21 9 5 9c-2.086 0-3.8 1.398-3.984 3.181A1 1 0 0 0 2 13h6.96q.04-.245.04-.5M7 6a2 2 0 1 0-4 0 2 2 0 0 0 4 0", 0.9, "#ffffff")));
		data.add(new ModelField(getName(), "name-field"));
		data.add(new ModelField(getLastName(), "last-name-field"));
		data.add(new ModelField(getMail(), "mail-field-user", createIcon("M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1zm13 2.383-4.708 2.825L15 11.105zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741M1 11.105l4.708-2.897L1 5.383z", 0.9, "#ffffff")));

		return data;
	}
}
