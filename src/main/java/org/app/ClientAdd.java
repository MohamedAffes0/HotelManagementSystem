package org.app;

import java.sql.*;
import org.database.DBConnect;
import org.models.PersonModel;

public class ClientAdd {
    public static boolean clientAdd(PersonModel client) {
        if (client.getMail() == null || client.getMail().isEmpty()) {
            System.err.println("L'email ne doit pas être vide.");
            return false;
        }
        if (ClientChecker.clientCheck(client.getId()) == ClientChecker.ClientStatus.CLIENT_FOUND) {
            System.err.println("Le client existe déjà.");
            return false;
            
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call add_client_hotel(?, ?, ?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, client.getId());
                stmt.setString(2, client.getName());
                stmt.setString(3, client.getLastName());
                stmt.setString(4, client.getMail());

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
    //     System.out.println(clientAdd(12345678, "med", "aa", "med@gmail.com"));
    // }
}
