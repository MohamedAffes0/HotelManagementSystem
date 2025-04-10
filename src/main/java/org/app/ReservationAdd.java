package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import org.app.ReservationChecker.ReservationDate;
import org.database.DBConnect;
import org.models.ReservationModel;

public class ReservationAdd {
    public static boolean reservationAdd(ReservationModel reservation) {
        // Vérification de la disponibilité de la chambre
        ArrayList<ReservationDate> reservationDates = ReservationChecker.reservationCheck(reservation.getId());
        if (reservationDates != null) {
            for (int i = 0; i < reservationDates.size(); i++) {
                if (reservationDates.get(i).startDate.before(reservation.getEndDate()) || 
                    reservationDates.get(i).endDate.after(reservation.getStartDate())) {
                        System.err.println("La chambre est déjà réservée pour cette période.");
                        return false; // Indique que la réservation échoue
                } else if (reservationDates.get(i).startDate.equals(reservation.getStartDate()) || 
                    reservationDates.get(i).endDate.equals(reservation.getEndDate())) {
                        System.err.println("La chambre est déjà réservée pour cette période.");
                        return false; // Indique que la réservation échoue
                }
            }
        }
        
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
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
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return false; // Indique que la connexion a échoué
            }
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
    //     System.out.println(reservationAdd(Date.valueOf("2025-04-2"), Date.valueOf("2025-04-2"), false, 3, 12345678, 1));
    // }
}
