package org.app;

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
    public static ArrayList<ReservationDate> reservationCheck(int id) {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<ReservationDate> reservationDates = new ArrayList<>();
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call check_reservation(?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id);
                stmt.registerOutParameter(2, OracleTypes.CURSOR);                

                stmt.execute();
                ResultSet result = null;
                try {
                    result = (ResultSet) stmt.getObject(2);
                    while (result.next()) {
                        Date startDate = result.getDate("date_debut");
                        Date endDate = result.getDate("date_fin");

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

                return reservationDates;
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
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
