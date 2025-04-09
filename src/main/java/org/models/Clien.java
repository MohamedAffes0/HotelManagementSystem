package org.models;

public class Clien {
    private int cin;
    private String nom;
    private String prenom;
    private String mail;

    public Clien(int cin, String nom, String prenom, String mail) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }

    public int getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }
}
