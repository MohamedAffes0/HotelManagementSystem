package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import java.util.ArrayList;

import org.database.DBConnect;
import org.models.RoomModel;

public class RoomSelect {
    public static ArrayList<RoomModel> roomSelect() {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<RoomModel> rooms = new ArrayList<>();
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call get_all_rooms(?) }";
                stmt = connection.prepareCall(sql);
                stmt.registerOutParameter(1, OracleTypes.CURSOR);                

                stmt.execute();
                ResultSet result = (ResultSet) stmt.getObject(1);
                while (result.next()) {
                    int id = result.getInt("id_chambre");
                    String type = result.getString("type_chambre");
                    int etage = result.getInt("etage");
                    int nb_personnes = result.getInt("nb_personnes");
                    float prix = result.getFloat("prix");
                    int etat = result.getInt("etat");

                    rooms.add(new RoomModel(id, type, etage, nb_personnes, prix, etat));
                }

                return rooms;
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     if (roomSelect() != null) {
    //         ArrayList<RoomModel> rooms = roomSelect();
    //         for (int i = 0; i < rooms.size(); i++) {
    //             System.out.println("ID: " + rooms.get(i).getIdChambre() + ", Type: " + rooms.get(i).getTypeChambre() +
    //                     ", Etage: " + rooms.get(i).getEtage() + ", Nombre de personnes: " + rooms.get(i).getNbPersonnes() +
    //                     ", Prix: " + rooms.get(i).getPrix() + ", Etat: " + rooms.get(i).getEtat());
    //         }
    //     }
    // }
}
