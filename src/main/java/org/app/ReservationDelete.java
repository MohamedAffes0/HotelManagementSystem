package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.database.DBConnect;

public class ReservationDelete {
    public static boolean reservationDelete(int id) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return false; // Indique que la connexion a échoué
            }

            String sql = "{ call delete_reservation(?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, id);

            stmt.execute();
            return true; // Indique que l'ajout a réussi

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
    //     System.out.println(reservationDelete(10));
    // }
}
