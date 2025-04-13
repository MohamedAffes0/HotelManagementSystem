package org.models;

public class PersonModel {
    private int cin;
    private String name;
    private String lastName;
    private String mail;

    public PersonModel(int cin, String name, String lastName, String mail) {

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

    public void setNom(String name) {
        this.name = name;
    }

    public void setPrenom(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
