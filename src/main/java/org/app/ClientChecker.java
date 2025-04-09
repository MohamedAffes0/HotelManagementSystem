package org.app;

import java.sql.*;

import org.database.DBConnect;

public class ClientChecker {
    public static enum ClientStatus {
        CLIENT_NOT_FOUND,
        CLIENT_FOUND,
        CONNEXION_FAILED
    }
    public static ClientStatus clientCheck(int cinText) {
        if (cinText == 0) {
            System.out.println("CIN non valide.");
            return ClientStatus.CLIENT_NOT_FOUND;
        }
        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call check_client(?, ?) }";
                stmt = connection.prepareCall(sql);
                stmt.setInt(1, cinText);
                stmt.registerOutParameter(2, Types.INTEGER);

                stmt.execute();
                int result = stmt.getInt(2);

                if (result == 1) {
                    System.out.println("Client existant.");
                    return ClientStatus.CLIENT_FOUND;
                } else {
                    System.out.println("Client non trouvé.");
                    return ClientStatus.CLIENT_NOT_FOUND;
                }
            } else {
                System.err.println("Échec de la connexion à la base de données.");
                return ClientStatus.CONNEXION_FAILED;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return ClientStatus.CONNEXION_FAILED;
        } finally {
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
    //     System.out.println(clientCheck(12345679)); // Test with a valid CIN
    // }
}
