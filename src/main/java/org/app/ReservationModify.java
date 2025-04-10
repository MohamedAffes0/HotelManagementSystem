package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.database.DBConnect;

public class ReservationModify {
        public static boolean reservationModify(int id, Date startDate, Date endDate, boolean isPaid) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call modify_reservation(?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id);
                stmt.setDate(2, new Date(startDate.getTime())); // convertir Date en java.sql.Date
                stmt.setDate(3, new Date(endDate.getTime()));
                stmt.setInt(4, isPaid ? 1 : 0);

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
    //     System.out.println(reservationModify(1, Date.valueOf("2025-02-20"), Date.valueOf("2025-02-21"), true));
    // }
}
