package org.app.client;

import java.sql.*;
import java.util.ArrayList;

import org.database.DBConnect;
import org.models.Person;

public class ClientAdd {

    public static enum CreationStatus {
        SUCCESS,
        DB_PROBLEM,
        CIN_EXISTS,
    }

    public static CreationStatus clientAdd(Person client, ArrayList<Person> clients) {

        // if (ClientChecker.clientCheck(client.getId()) == ClientChecker.ClientStatus.CLIENT_FOUND) {
        //     System.err.println("Le client existe déjà.");
        //     return false;
            
        // }

        // verification de l'existence du cin dans la base de données
        boolean clientExists = false;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getCin() == client.getCin()) {
                clientExists = true;
                break;
            }
        }
        if (clientExists) {
            return CreationStatus.CIN_EXISTS;
        }

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return CreationStatus.DB_PROBLEM; // Indique que la connexion a échoué
            }

            String sql = "{ call add_client_hotel(?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, client.getCin());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getLastName());
            stmt.setString(4, client.getMail());

            stmt.execute();
            return CreationStatus.SUCCESS; // Indique que l'ajout a réussi
        } catch (SQLException exception) {
            exception.printStackTrace();
            return CreationStatus.DB_PROBLEM;
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
