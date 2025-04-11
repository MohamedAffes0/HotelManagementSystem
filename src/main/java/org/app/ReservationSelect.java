package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import org.database.DBConnect;
import org.models.ReservationModel;

import oracle.jdbc.OracleTypes;

public class ReservationSelect {
    public static ArrayList<ReservationModel> userSelect() {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<ReservationModel> reservations = new ArrayList<>();
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return null; // Indique que la connexion a échoué
            }

            String sql = "{ call get_all_reservation(?) }";
            stmt = connection.prepareCall(sql);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);                

            stmt.execute();
            ResultSet result = null;
            try {
                result = (ResultSet) stmt.getObject(1);
                while (result.next()) {
                    int id = result.getInt("id_reservation");
                    Date startDate = result.getDate("date_debut");
                    Date endDate = result.getDate("date_fin");
                    boolean isPaid = result.getInt("paid") == 1? true : false;
                    int employee = result.getInt("employe");
                    int hotelClient = result.getInt("client_hotel");
                    int room = result.getInt("chambre");

                    reservations.add(new ReservationModel(id, startDate, endDate, isPaid, employee, hotelClient, room));
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

            return reservations;

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
    //     ArrayList<ReservationModel> reservations = userSelect();
    //     for (int i = 0; i < reservations.size(); i++) {
    //         System.out.println(reservations.get(i).getIdReservation() + " " + 
    //         reservations.get(i).getDateDebut() + " " + reservations.get(i).getDateFin() 
    //         + " " + reservations.get(i).isPaid() + " " + reservations.get(i).getEmploye() 
    //         + " " + reservations.get(i).getClientHotel() + " " + reservations.get(i).getChambre());
    //     }
    // }
}
