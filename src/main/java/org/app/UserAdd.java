package org.app;

import java.sql.*;
import org.database.DBConnect;

public class UserAdd {
    public static boolean userAdd(int cin, String nomText, String prenomText, String emailText, String passwordText,
            boolean isAdmin, boolean isActive) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call add_employe(?, ?, ?, ?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, cin);
                stmt.setString(2, nomText);
                stmt.setString(3, prenomText);
                stmt.setString(4, emailText);
                stmt.setString(5, passwordText);
                stmt.setInt(6, isAdmin ? 1 : 0);
                stmt.setInt(7, isActive ? 1 : 0);

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
        System.out.println(userAdd(11196672, "med", "affes", "med@gmail.com", "1234", true, true));
    }
}
