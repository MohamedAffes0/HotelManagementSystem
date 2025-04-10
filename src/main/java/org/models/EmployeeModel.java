package org.models;

final public class EmployeeModel extends PersonModel {
    private String password;
    private boolean isAdmin;
    private boolean isActive;

    public EmployeeModel(int id, String name, String lastName, String mail, String password, boolean isAdmin, boolean isActive) {
        super(id, name, lastName, mail);
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public int getCin() {
        return super.getId();
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
}
