package org.app;

import java.sql.*;
import org.database.DBConnect;

public class ClientAdd {
    public static boolean clientAdd(int cinText, String nomText, String prenomText, String mailText) {
        if (cinText == 0) {
            System.err.println("Le CIN ne doit pas être vide.");
            return false;
        }
        if (mailText == null || mailText.isEmpty()) {
            System.err.println("L'email ne doit pas être vide.");
            return false;
        }
        if (ClientChecker.clientCheck(cinText) == ClientChecker.ClientStatus.CLIENT_FOUND) {
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
                stmt.setInt(1, cinText);
                stmt.setString(2, nomText);
                stmt.setString(3, prenomText);
                stmt.setString(4, mailText);

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

    // public static void main(String[] args) {
    //     System.out.println(clientAdd(12345678, "med", "aa", "med@gmail.com"));
    // }
}
