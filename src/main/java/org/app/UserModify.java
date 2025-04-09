package org.app;

import java.sql.*;

import org.database.DBConnect;

public class UserModify {
    public static boolean userModify(int id, boolean isAdmin, boolean isActive) {
        if (id <= 0) {
            System.err.println("Le CIN doit être supérieur à 0.");
            return false;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call modify_user(?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id);
                stmt.setInt(2, isAdmin ? 1 : 0);
                stmt.setInt(3, isActive ? 1 : 0);

                stmt.execute();
                return true; // Indique que l'ajout a réussi
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
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
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     System.out.println(userModify(2, true, true));
    // }
}
