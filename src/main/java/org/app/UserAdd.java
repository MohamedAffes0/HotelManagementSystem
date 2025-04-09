package org.app;

import java.sql.*;
import org.database.DBConnect;

public class UserAdd {
    public static boolean userAdd(String nomText, String prenomText, String emailText, String passwordText,
            boolean isAdmin, boolean isActive) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call add_employe(?, ?, ?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                // stmt.setInt(1, cin);
                stmt.setString(1, nomText);
                stmt.setString(2, prenomText);
                stmt.setString(3, emailText);
                stmt.setString(4, passwordText);
                stmt.setInt(5, isAdmin ? 1 : 0);
                stmt.setInt(6, isActive ? 1 : 0);

                stmt.execute();
                return true; // Indique que l'ajout a réussi
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public static void main(String[] args) {
        System.out.println(userAdd("maisa", "ben mouuurad", "maisa@gmail.com", "1234", false, false));
    }
}
