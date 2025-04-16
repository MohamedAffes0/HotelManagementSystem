package org.app.room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.database.DBConnect;
import org.models.RoomModel;
import org.models.RoomModel.RoomState;
import org.models.RoomModel.RoomType;

public class RoomRequested {
    public static RoomModel roomRequested() {
        Connection connection = null;
        CallableStatement stmt = null;
        RoomModel room = null;
        int id;
        RoomType roomType;
        int floor;
        int capacity;
        float price;
        RoomState state;

        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return null; // Indique que la connexion a échoué
            }

            String sql = "{ call requested_room(?, ?, ?, ?, ?,?) }";
            stmt = connection.prepareCall(sql);

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.registerOutParameter(2, Types.VARCHAR);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.registerOutParameter(5, Types.FLOAT);
            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.execute();

            id = stmt.getInt(1);
            switch (stmt.getString(2)) {
                case "simple":
                    roomType = RoomType.SIMPLE;
                    break;
                case "double":
                    roomType = RoomType.DOUBLE;
                    break;
                case "suite":
                    roomType = RoomType.SUITE;
                    break;
                default:
                    roomType = RoomType.SIMPLE; // Valeur par défaut si le type n'est pas reconnu
                    break;
            }
            floor = stmt.getInt(3);
            capacity = stmt.getInt(4);
            price = stmt.getFloat(5);
            switch (stmt.getInt(6)) {
                case 0:
                    state = RoomState.LIBRE;
                    break;
                case 1:
                    state = RoomState.OCCUPEE;
                    break;
                case 2:
                    state = RoomState.MAINTENANCE;
                    break;
                default:
                    state = RoomState.LIBRE; // Valeur par défaut si l'état n'est pas reconnu
                    break;
            }
            
            if (id != 0) {
                room = new RoomModel(id, roomType, floor, capacity, price, state);
            } else {
                System.out.println("Aucune chambre disponible.");
            }

            return room;

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
}
