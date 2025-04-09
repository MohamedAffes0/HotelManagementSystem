package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.database.DBConnect;

public class UserDelete {
    public static boolean userDelete(int id) {
        if (id <= 0) {
            System.err.println("Le CIN doit être supérieur à 0.");
            return false;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call delete_user(?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id);

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

    // public static void main(String[] args) {
    //     System.out.println(userDelete(1)); // Test de la méthode userDelete
    // }
}
