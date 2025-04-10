package org.models;

public class PersonModel {
    private int id;
    private String name;
    private String lastName;
    private String mail;

    public PersonModel(int id, String name, String lastName, String mail) {

        if (id <= 0) {
            throw new IllegalArgumentException("L'ID doit être supérieur à 0.");
        }

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
