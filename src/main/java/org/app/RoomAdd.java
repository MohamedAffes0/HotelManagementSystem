package org.app;

import java.sql.*;
import org.database.DBConnect;

public class RoomAdd {
    public static boolean roomAdd(int id, String typeText, int etage, int nbPersonnes, 
        float prix, int etat) {
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
                stmt.setInt(6, etat);

                stmt.execute();
                return true; // Indique que l'ajout a réussi
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(roomAdd(1, "simple", 1, 2, 100.0f, 1)); // Exemple d'utilisation
    }
}
