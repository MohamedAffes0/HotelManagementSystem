package org.app.user;

import java.sql.*;
import java.util.ArrayList;

import org.database.DBConnect;
import org.models.Employee;

public class UserAdd {
    public static enum CreationStatus {
        SUCCESS,
        USER_EXISTS,
        CONNEXION_FAILED,
        EMPTY_FIELD
    }

    // id a ignorer
    public static CreationStatus userAdd(Employee employee, ArrayList<Employee> employees) {
        // Vérification des champs vides
        if (employee.getMail() == null || employee.getMail().isEmpty()) {
            System.err.println("L'email ne doit pas être vide.");
            return CreationStatus.EMPTY_FIELD;
        }

        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
            System.err.println("Le mot de passe ne doit pas être vide.");
            return CreationStatus.EMPTY_FIELD;
        }

        // // Vérification de l'existence de l'utilisateur
        // if (LoginChecker.loginCheck(employee.getMail(), employee.getPassword()) == LoginStatus.INACTIVE_USER || 
        //     LoginChecker.loginCheck(employee.getMail(), employee.getPassword()) == LoginStatus.ADMIN_USER ||
        //     LoginChecker.loginCheck(employee.getMail(), employee.getPassword()) == LoginStatus.NORMAL_USER) {
        //     System.err.println("L'utilisateur existe déjà.");
        //     return CreationStatus.USER_EXISTS;
        // }

        // Vérification de l'existence de l'utilisateur
        boolean userExists = false;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getCin() == employee.getCin()) {
                userExists = true;
            }
        }
        if (userExists) {
            return CreationStatus.USER_EXISTS;
        }

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return CreationStatus.CONNEXION_FAILED; // Indique que la connexion a échoué
            }

            String sql = "{ call add_employe(?, ?, ?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getMail());
            stmt.setString(4, employee.getPassword());
            stmt.setInt(5, employee.isAdmin() ? 1 : 0);
            stmt.setInt(6, employee.isActive() ? 1 : 0);

            stmt.execute();
            return CreationStatus.SUCCESS; // Indique que l'ajout a réussi

        } catch (SQLException exception) {
            exception.printStackTrace();
            return CreationStatus.CONNEXION_FAILED; // Indique que la connexion a échoué
        } finally {
            // toujour executer le bloc finally
            // Fermeture des ressources JDBC
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     System.out.println(userAdd("maisa", "ben mouuurad", "maisa@gmail.com", "1234", false, false));
    // }
}
