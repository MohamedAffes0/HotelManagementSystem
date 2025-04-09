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
    private static class ReservationDate {
        private Date dateDebut;
        private Date dateFin;

        public ReservationDate(Date dateDebut, Date dateFin) {
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
        }
    }
    public static ArrayList<ReservationDate> reservationCheck(int id_chambre) {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<ReservationDate> reservationDate = new ArrayList<>();
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call check_reservation(?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id_chambre);
                stmt.registerOutParameter(2, OracleTypes.CURSOR);                

                stmt.execute();
                ResultSet result = null;
                try {
                    result = (ResultSet) stmt.getObject(2);
                    while (result.next()) {
                        Date dateDebut = result.getDate("date_debut");
                        Date DateFin = result.getDate("date_fin");

                        reservationDate.add(new ReservationDate(dateDebut, DateFin));
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

                return reservationDate;
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

    public static void main(String[] args) {
        // Exemple d'utilisation de la méthode reservationCheck
        int id = 1; // Remplacez par l'ID de la réservation que vous souhaitez vérifier
        ArrayList<ReservationDate> reservations = reservationCheck(id);
        if (reservations != null) {
            for (ReservationDate reservation : reservations) {
                System.out.println("Date de début : " + reservation.dateDebut + ", Date de fin : " + reservation.dateFin);
            }
        } else {
            System.out.println("Aucune réservation trouvée.");
        }
    }
}
