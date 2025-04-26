package org.app.client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.database.DBConnect;
import org.models.Person;

public class ClientModify {
    public static boolean clientModify(Person client) {
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return false; // Indique que la connexion a échoué
            }

            String sql = "{ call modify_client(?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, client.getCin());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getLastName());
            stmt.setString(4, client.getMail());

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
    //     Person person = new Person(12345679, "meddd", "affess", "xx@gmail.com");
    //     System.out.println(clientModify(person));
    // }
}
