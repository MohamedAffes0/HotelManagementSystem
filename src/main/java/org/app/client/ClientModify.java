package org.app.client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.app.EmailChecker;
import org.app.user.ControllerException;
import org.database.DBConnect;
import org.models.Person;

public class ClientModify {
    public static void clientModify(Person client) throws ControllerException {

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                throw new ControllerException("Échec de la connexion à la base de données.");
            }

            if (!EmailChecker.isValid(client.getMail())) {
                System.out.println("Email invalide");
                throw new ControllerException("Email invalide");
            }

            if (client.getName() == null || client.getName().isEmpty()) {
                System.out.println("Nom invalide");
                throw new ControllerException("Nom invalide");
            }

            if (client.getLastName() == null || client.getLastName().isEmpty()) {
                System.out.println("Prénom invalide");
                throw new ControllerException("Prénom invalide");
            }

            String sql = "{ call modify_client(?, ?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, client.getCin());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getLastName());
            stmt.setString(4, client.getMail());

            stmt.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new ControllerException("Erreur de connexion à la base de données");
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
