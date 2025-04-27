package org.controllers.user;

import java.sql.*;

import org.database.DBConnect;

public class UserModify {
    public static void userModify(int id, boolean isAdmin, boolean isActive) throws ControllerException {

        // Vérification de la validité de l'ID
        if (id <= 0) {
            System.err.println("Le CIN doit être supérieur à 0.");
            throw new ControllerException("Le CIN doit être supérieur à 0.");
        }

        Connection connection = null;
        CallableStatement stmt = null;
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                throw new ControllerException("Échec de la connexion à la base de données.");
            }

            String sql = "{ call modify_user(?, ?, ?) }";
            stmt = connection.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, isAdmin ? 1 : 0);
            stmt.setInt(3, isActive ? 1 : 0);

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
    //     System.out.println(userModify(2, true, true));
    // }
}
