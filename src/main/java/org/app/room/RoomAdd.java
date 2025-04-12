package org.app.room;

import java.sql.*;
import org.database.DBConnect;
import org.models.RoomModel;
import org.models.RoomModel.RoomState;

public class RoomAdd {
    public static boolean roomAdd(RoomModel room) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return false; // Indique que la connexion a échoué
            }

            String sql = "{ call add_room(?, ?, ?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, room.getId());
            stmt.setString(2, room.getRoomType());
            stmt.setInt(3, room.getFloor());
            stmt.setInt(4, room.getNumberOfPeople());
            stmt.setFloat(5, room.getPrice());
            switch (room.getState()) {
                case LIBRE:
                stmt.setInt(6, 0); // 0 for LIBRE
                    break;
                case OCCUPEE:
                stmt.setInt(6, 1); // 1 for OCCUPEE
                    break;
                case MAINTENANCE:
                stmt.setInt(6, 2); // 2 for MAINTENANCE
                    break;
            }

            stmt.execute();
            return true; // Indique que l'ajout a réussi

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
    //     RoomModel room = new RoomModel(5, "double", 5, 14, 500, RoomState.OCCUPEE);
    //     System.out.println(roomAdd(room));
    // }
}
