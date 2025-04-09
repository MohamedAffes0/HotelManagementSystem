package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.database.DBConnect;
import org.models.RoomModel.RoomState;

public class RoomModify {
    public static boolean roomModify(int id, int nbPersonnes, float prix, RoomState etat) {
        if (id == 0) {
            System.err.println("L'ID ne doit pas être vide.");
            return false;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call modify_room(?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id);
                stmt.setInt(2, nbPersonnes);
                stmt.setFloat(3, prix);
                switch (etat) {
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
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return false;
            }
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
