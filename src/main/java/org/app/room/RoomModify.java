package org.app.room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.database.DBConnect;
import org.models.RoomModel.RoomState;

public class RoomModify {
    public static boolean roomModify(int id, int numberOfPeople, float price, RoomState state) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return false; // Indique que la connexion a échoué
            }

            String sql = "{ call modify_room(?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, numberOfPeople);
            stmt.setFloat(3, price);
            switch (state) {
                case LIBRE:
                    stmt.setInt(4, 0); // 0 for LIBRE
                    break;
                case OCCUPEE:
                    stmt.setInt(4, 1); // 1 for OCCUPEE
                    break;
                case MAINTENANCE:
                    stmt.setInt(4, 2); // 2 for MAINTENANCE
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
    //     System.out.println(roomModify(2, 3, 100.9f, 0)); // Exemple d'utilisation
    // }
}
