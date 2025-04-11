package org.app.reservation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

import org.app.reservation.ReservationChecker.ReservationDate;
import org.database.DBConnect;
import org.models.ReservationModel;

public class ReservationAdd {
    // id a ignorer
    public static boolean reservationAdd(ReservationModel reservation) {

        // verification de la disponibilité de la chambre
        ReservationDate reservationDate = new ReservationDate(reservation.getStartDate(), reservation.getEndDate());
        if (ReservationChecker.reservationCheck(reservation.getRoom(), 0, reservationDate) == false) {
            System.err.println("La chambre est déjà réservée pour cette période.");
            return false; // Indique que la réservation échoue
        }

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return false; // Indique que la connexion a échoué
            }

            String sql = "{ call add_reservation(?, ?, ?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);

            stmt.setDate(1, new Date(reservation.getStartDate().getTime())); // convertir Date en java.sql.Date
            stmt.setDate(2, new Date(reservation.getEndDate().getTime()));
            stmt.setInt(3, reservation.isPaid() ? 1 : 0);
            stmt.setInt(4, reservation.getEmployee());
            stmt.setInt(5, reservation.getHotelClient());
            stmt.setInt(6, reservation.getRoom());

            stmt.execute();
            return true; // Indique que l'ajout a réussi

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false; // Indique que la connexion a échoué
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
    //     // Test de la méthode reservationAdd
    //     ReservationModel reservation = new ReservationModel(1, Date.valueOf("2005-12-23"), Date.valueOf("2005-12-24"),
    //             false, 3, 12345678, 10);
    //     boolean result = reservationAdd(reservation);
    //     System.out.println("Reservation added: " + result);
    // }
}
