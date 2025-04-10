package org.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.database.DBConnect;
import org.models.EmployeeModel;

import oracle.jdbc.OracleTypes;

public class UserSelect {
    public static ArrayList<EmployeeModel> userSelect() {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<EmployeeModel> users = new ArrayList<>();
        try {
            connection = DBConnect.connect();
            if (connection != null) {
                String sql = "{ call get_all_users(?) }";
                stmt = connection.prepareCall(sql);
                stmt.registerOutParameter(1, OracleTypes.CURSOR);                

                stmt.execute();
                ResultSet result = null;
                try {
                    result = (ResultSet) stmt.getObject(1);
                    while (result.next()) {
                        int id = result.getInt("id");
                        String name = result.getString("nom");
                        String lastName = result.getString("prenom");
                        String mail = result.getString("mail");
                        String password = result.getString("mdp");
                        boolean isAdmin = result.getInt("is_admin") == 1 ?true: false;
                        boolean isActive = result.getInt("is_active") == 1 ?true: false;

                        users.add(new EmployeeModel(id, name, lastName, mail, password, isAdmin, isActive));
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

                return users;
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
    //     ArrayList<EmployeeModel> users = userSelect();
    //     if (users != null) {
    //         for (EmployeeModel user : users) {
    //             System.out.println(user.getNom() + " " + user.getPrenom() + " " + user.getMail() + " " + user.getMdp() + " " + user.isAdmin() + " " + user.isActive());
    //         }
    //     } else {
    //         System.out.println("Aucun utilisateur trouvé.");
    //     }
    // }
}
