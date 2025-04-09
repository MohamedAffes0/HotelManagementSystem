package org.app;

import java.sql.*;
import org.database.DBConnect;

public class LoginChecker {
    public static enum LoginStatus {
        USER_NOT_FOUND,
        INACTIVE_USER,
        ADMIN_USER,
        NORMAL_USER,
        CONNEXION_FAILED,
        EMPTY_FIELD
    }
    public static LoginStatus loginCheck(String emailText, String passwordText) {
        Connection connection = null;
        CallableStatement stmt = null;
        if (emailText == null || emailText.isEmpty()) {
            System.out.println("L'email ne doit pas être vide.");
            return LoginStatus.EMPTY_FIELD;
        }
        if (passwordText == null || passwordText.isEmpty()) {
            System.out.println("Le mot de passe ne doit pas être vide.");
            return LoginStatus.EMPTY_FIELD;
        }
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call check_login(?, ?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setString(1, emailText);
                stmt.setString(2, passwordText);
                stmt.registerOutParameter(3, Types.INTEGER);
                stmt.registerOutParameter(4, Types.INTEGER);
                stmt.registerOutParameter(5, Types.INTEGER);

                stmt.execute();
                int result = stmt.getInt(3);
                int isAdmin = stmt.getInt(4);
                int isActive = stmt.getInt(5);

                if (result == 1) {
                    System.out.println("Utilisateur existant.");
                    if (isActive == 0) {
                        return LoginStatus.INACTIVE_USER;
                    }
                    else if (isAdmin == 1) {
                        return LoginStatus.ADMIN_USER; // Admin
                    } else {
                        return LoginStatus.NORMAL_USER; // Utilisateur normal
                    }
                } else {
                    System.out.println("Utilisateur non trouvé.");
                    return LoginStatus.USER_NOT_FOUND ;
                }
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return LoginStatus.CONNEXION_FAILED;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return LoginStatus.CONNEXION_FAILED;
        } finally {
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
    //     System.err.println(loginCheck("maisa@gmail.com", "1234"));
    // }
}
