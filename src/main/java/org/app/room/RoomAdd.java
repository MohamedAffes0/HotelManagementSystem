package org.app.room;

import java.sql.*;
import java.util.ArrayList;

import org.database.DBConnect;
import org.models.RoomModel;
// import org.models.RoomModel.RoomState;
// import org.models.RoomModel.RoomType;

public class RoomAdd {
    
    public static enum AddResult{
        SUCCESS,
        DB_PROBLEM,
        ID_EXISTS,
    }

    public static AddResult roomAdd(RoomModel room, ArrayList<RoomModel> rooms) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return AddResult.DB_PROBLEM; // Indique que la connexion a échoué
            }

            // // Vérification de l'existence de l'ID dans la base de données
            // String checkSql = "{ call check_room(?, ?) }";
            // stmt = connection.prepareCall(checkSql);
            // stmt.setInt(1, room.getId()); 
            // stmt.registerOutParameter(2, Types.INTEGER); // Enregistrement du paramètre de sortie
            // stmt.execute();
            // int exists = stmt.getInt(2); // Récupération de la valeur du paramètre de sortie
            // if (exists == 1) {
            //     return AddResult.ID_EXISTS; // Indique que l'ID existe déjà
            // }

            // Vérification de l'existence de l'ID dans la base de données
            boolean roomExists = false;
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getId() == room.getId()) {
                    roomExists = true;
                    break;
                }
            }
            if (roomExists) {
                return AddResult.ID_EXISTS;
            }

            // Préparation de la requête d'insertion
            String sql = "{ call add_room(?, ?, ?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, room.getId());
            switch (room.getRoomType()) {
                case SIMPLE:
                    stmt.setString(2, "simple");
                    break;
                case DOUBLE:
                    stmt.setString(2, "double");
                    break;
                case SUITE:
                    stmt.setString(2, "suite");
                    break;
            }
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
            return AddResult.SUCCESS; // Indique que l'ajout a réussi

        } catch (SQLException exception) {
            exception.printStackTrace();
            return AddResult.DB_PROBLEM; // Indique qu'il y a eu un problème avec la base de données
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
    //     RoomModel room = new RoomModel(5, RoomType.SIMPLE, 5, 14, 500, RoomState.OCCUPEE);
    //     System.out.println(roomAdd(room));
    // }
}
