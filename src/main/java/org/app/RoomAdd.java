package org.app;

import java.sql.*;
import org.database.DBConnect;
import org.models.RoomModel.RoomState;

public class RoomAdd {
    public static boolean roomAdd(int id, String typeText, int etage, int nbPersonnes, 
        float prix, RoomState etat) {
        if (id == 0) {
            System.err.println("L'ID ne doit pas être vide.");
            return false;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call add_room(?, ?, ?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, id);
                stmt.setString(2, typeText);
                stmt.setInt(3, etage);
                stmt.setInt(4, nbPersonnes);
                stmt.setFloat(5, prix);
                switch (etat) {
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
    //     System.out.println(roomAdd(11, "simple", 1, 2, 100.0f, RoomState.LIBRE)); // Exemple d'utilisation
    // }
}
