package org.controllers.room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.database.DBConnect;

import oracle.jdbc.OracleTypes;

public class AvaliableRooms {
    public ArrayList<Integer> getAvaliableRooms(Date startDate, Date endDate, int capacity) {
        ArrayList<Integer> availableRooms = new ArrayList<>();
        Connection connection = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;

        if (startDate == null || endDate == null) {
            System.out.println("Les dates de début et de fin ne peuvent pas être nulles.");
            return null; // Retourne une liste vide si les dates sont nulles
        }

        if (startDate.after(endDate)) {
            System.out.println("La date de début ne peut pas être après la date de fin.");
            return null; // Retourne une liste vide si les dates sont invalides
        }

        try {

            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return null; // Indique que la connexion a échoué
            }

            String sql = "{ ? = call get_available_rooms(?, ?, ?) }";
            stmt = connection.prepareCall(sql);

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            stmt.setInt(4, capacity);

            stmt.execute();

            resultSet = (ResultSet) stmt.getObject(1);

            while (resultSet.next()) {
                int roomId = resultSet.getInt("id_chambre");
                availableRooms.add(roomId);
            }

            return availableRooms;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        } finally {
            // toujour executer le bloc finally
            // Fermeture des ressources JDBC
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
    //     AvaliableRooms availableRooms = new AvaliableRooms();
    //     Date startDate = Date.valueOf("2025-04-26");
    //     Date endDate = Date.valueOf("2025-04-26");
    //     int capacity = 2;

    //     ArrayList<Integer> rooms = availableRooms.getAvaliableRooms(startDate, endDate, capacity);
    //     if (rooms != null) {
    //         System.out.println("Chambres disponibles : " + rooms);
    //     } else {
    //         System.out.println("Aucune chambre disponible ou erreur lors de la récupération des chambres.");
    //     }
    // }
}
