package org.models;

final public class EmployeeModel extends PersonModel {
    private String mdp;
    private boolean is_admin;
    private boolean is_active;

    public EmployeeModel(int id, String nom, String prenom, String mail, String mdp, boolean is_admin, boolean is_active) {
        super(id, nom, prenom, mail);
        this.mdp = mdp;
        this.is_admin = is_admin;
        this.is_active = is_active;
    }

    public int getCin() {
        return super.getId();
    }

    public String getNom() {
        return super.getNom();
    }

    public String getPrenom() {
        return super.getPrenom();
    }

    public String getMail() {
        return super.getMail();
    }

    public String getMdp() {
        return mdp;
    }

    public boolean isAdmin() {
        return is_admin;
    }

    public boolean isActive() {
        return is_active;
    }

    public void setAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
