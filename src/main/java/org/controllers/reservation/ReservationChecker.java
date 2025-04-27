package org.controllers.reservation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.database.DBConnect;

import oracle.jdbc.OracleTypes;

public class ReservationChecker {
    public static class ReservationDate {
        public Date startDate;
        public Date endDate;

        public ReservationDate(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
    public static boolean reservationCheck(int roomId, int reservationId, ReservationDate actualReservation) {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<ReservationDate> reservationDates = new ArrayList<>();
        try {
            connection = DBConnect.connect();
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return false;
            }

            String sql = "{ call check_reservation(?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, roomId);
            stmt.setInt(2, reservationId);
            stmt.registerOutParameter(3, OracleTypes.CURSOR);                

            stmt.execute();
            ResultSet result = null;
            try {
                result = (ResultSet) stmt.getObject(3);
                while (result.next()) {
                    // Date startDate = result.getDate("date_debut");
                    // Date endDate = result.getDate("date_fin");
                    Date startDate = result.getDate(1); // colonne 1 = date_debut
                    Date endDate = result.getDate(2);   // colonne 2 = date_fin

                    reservationDates.add(new ReservationDate(startDate, endDate));
                }
            } finally {
                if (result != null) {
                    try {
                        result.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            // // affichage de la liste des réservations
            // for (int i = 0; i < reservationDates.size(); i++) {
            //     System.out.println("Date de début : " + reservationDates.get(i).startDate + ", Date de fin : " + reservationDates.get(i).endDate);
            // }

            if (reservationDates != null) {
                for (int i = 0; i < reservationDates.size(); i++) {
                    Date existingStart = reservationDates.get(i).startDate;
                    Date existingEnd = reservationDates.get(i).endDate;

                    // Vérification de la disponibilité de la chambre
                    if ( !actualReservation.endDate.before(existingStart) &&
                        !actualReservation.startDate.after(existingEnd)) {
                        System.out.println("La chambre est déjà réservée pour cette période.");
                        System.out.println("Date de début : " + existingStart + ", Date de fin : " + existingEnd);
                        return false; // Indique que la réservation échoue
                    }
                }
            }

            return true;

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
    //     // Exemple d'utilisation de la méthode reservationCheck
    //     int id = 1; // Remplacez par l'ID de la réservation que vous souhaitez vérifier
    //     ArrayList<ReservationDate> reservations = reservationCheck(id);
    //     if (reservations != null) {
    //         for (ReservationDate reservation : reservations) {
    //             System.out.println("Date de début : " + reservation.startDate + ", Date de fin : " + reservation.endDate);
    //         }
    //     } else {
    //         System.out.println("Aucune réservation trouvée.");
    //     }
    // }
}
