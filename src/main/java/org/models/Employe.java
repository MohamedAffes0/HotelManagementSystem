package org.models;

public class Employe {
    private int cin;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private boolean is_admin;

    public Employe(int cin, String nom, String prenom, String mail, String mdp, boolean is_admin) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.is_admin = is_admin;
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

    public String getMdp() {
        return mdp;
    }

    public boolean isAdmin() {
        return is_admin;
    }

    public void setAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
