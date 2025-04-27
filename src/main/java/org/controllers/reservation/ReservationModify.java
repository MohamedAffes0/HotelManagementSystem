package org.controllers.reservation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.controllers.reservation.ReservationChecker.ReservationDate;
import org.database.DBConnect;
import org.controllers.user.ControllerException;

public class ReservationModify {
        public static void reservationModify(int id,int roomId, Date startDate, 
            Date endDate, boolean isPaid) throws ControllerException {
        
        //verification de la disponibilité de la chambre
        ReservationDate reservationDate = new ReservationDate(startDate, endDate);
        if (ReservationChecker.reservationCheck(roomId, id, reservationDate) == false) {
            System.err.println("La chambre est déjà réservée pour cette période.");
            throw new ControllerException("La chambre est déjà réservée pour cette période.");
        }

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                throw new ControllerException("Échec de la connexion à la base de données.");
            }

            String sql = "{ call modify_reservation(?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setDate(2, new Date(startDate.getTime())); // convertir Date en java.sql.Date
            stmt.setDate(3, new Date(endDate.getTime()));
            stmt.setInt(4, isPaid ? 1 : 0);

            stmt.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new ControllerException("Erreur de connexion à la base de données");
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
    //     System.out.println(reservationModify(13, 1,Date.valueOf("2025-01-01"), Date.valueOf("2025-01-02"), true));
    // }
}
