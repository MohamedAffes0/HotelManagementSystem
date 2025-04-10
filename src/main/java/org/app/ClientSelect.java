package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.database.DBConnect;
import org.models.PersonModel;

import oracle.jdbc.OracleTypes;

public class ClientSelect {
    public static ArrayList<PersonModel> userSelect() {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<PersonModel> clients = new ArrayList<>();
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call get_all_clients(?) }";
                stmt = connection.prepareCall(sql);
                stmt.registerOutParameter(1, OracleTypes.CURSOR);                

                stmt.execute();
                ResultSet result = null;
                try {
                    result = (ResultSet) stmt.getObject(1);
                    while (result.next()) {
                        int cin = result.getInt("cin");
                        String name = result.getString("nom");
                        String lastName = result.getString("prenom");
                        String mail = result.getString("mail");

                        clients.add(new PersonModel(cin, name, lastName, mail));
                    }
                } finally {
                    if (result != null) {
                        try {
                            result.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return clients;
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
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
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     ArrayList<PersonModel> clients = userSelect();
    //     if (clients != null) {
    //         for (PersonModel client : clients) {
    //             System.out.println("Cin: " + client.getId() + ", Nom: " + client.getNom() + ", Prenom: " + client.getPrenom() + ", Mail: " + client.getMail());
    //         }
    //     } else {
    //         System.out.println("Aucun client trouvé.");
    //     }
    // }
}
