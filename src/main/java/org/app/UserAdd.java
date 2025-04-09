package org.app;

import java.sql.*;
import org.database.DBConnect;
import org.app.LoginChecker.LoginStatus;

public class UserAdd {
    public static enum CreationStatus {
        SUCCESS,
        USER_EXISTS,
        CONNEXION_FAILED,
        EMPTY_FIELD
    }
    public static CreationStatus userAdd(String nomText, String prenomText, String emailText, String passwordText,
            boolean isAdmin, boolean isActive) {
        if (emailText == null || emailText.isEmpty()) {
            System.err.println("L'email ne doit pas être vide.");
            return CreationStatus.EMPTY_FIELD;
        }
        if (passwordText == null || passwordText.isEmpty()) {
            System.err.println("Le mot de passe ne doit pas être vide.");
            return CreationStatus.EMPTY_FIELD;
        }
        if (LoginChecker.loginCheck(emailText, passwordText) == LoginStatus.INACTIVE_USER || 
            LoginChecker.loginCheck(emailText, passwordText) == LoginStatus.ADMIN_USER ||
            LoginChecker.loginCheck(emailText, passwordText) == LoginStatus.NORMAL_USER) {
            System.err.println("L'utilisateur existe déjà.");
            return CreationStatus.USER_EXISTS;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call add_employe(?, ?, ?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setString(1, nomText);
                stmt.setString(2, prenomText);
                stmt.setString(3, emailText);
                stmt.setString(4, passwordText);
                stmt.setInt(5, isAdmin ? 1 : 0);
                stmt.setInt(6, isActive ? 1 : 0);

                stmt.execute();
                return CreationStatus.SUCCESS; // Indique que l'ajout a réussi
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return CreationStatus.CONNEXION_FAILED; // Indique que la connexion a échoué
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     System.out.println(userAdd("maisa", "ben mouuurad", "maisa@gmail.com", "1234", false, false));
    // }
}
