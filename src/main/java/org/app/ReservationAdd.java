package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

import org.database.DBConnect;

public class ReservationAdd {
    public static boolean reservationAdd(Date dateDebut, Date dateFin, boolean isPais, 
        int idEmploye, int idClient, int idChambre) {
        if (dateDebut == null || dateFin == null || dateDebut.after(dateFin) ||
            idEmploye <= 0 || idClient <= 0 || idChambre <= 0) {
            System.err.println("Les champs de réservation ne doivent pas être vides ou invalides.");
            return false;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call add_reservation(?, ?, ?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);

                stmt.setDate(1, new Date(dateDebut.getTime())); // convertir Date en java.sql.Date
                stmt.setDate(2, new Date(dateFin.getTime()));
                stmt.setInt(3, isPais ? 1 : 0);
                stmt.setInt(4, idEmploye);
                stmt.setInt(5, idClient);
                stmt.setInt(6, idChambre);

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
    //     System.out.println(reservationAdd(Date.valueOf("2025-01-20"), Date.valueOf("2025-01-21"), false, 3, 12345678, 1));
    // }
}
