package org.controllers.user;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.controllers.DBLoader;
import org.database.DBConnect;
import org.models.Employee;

import oracle.jdbc.OracleTypes;

public class UserSelect extends DBLoader{
    private ArrayList<Employee> data;

	public UserSelect() {
		data = new ArrayList<>();
		load();
	}
	
	public ArrayList<Employee> getData() {
		return data;
	}

	public void load() {
		data = dataFromDB();
	}

    public static ArrayList<Employee> dataFromDB() {
        Connection connection = null;
        CallableStatement stmt = null;
        ArrayList<Employee> users = new ArrayList<>();
        try {
            connection = DBConnect.connect();

            // Vérification de la connexion
            if (connection == null) {
                System.err.println("Échec de la connexion à la base de données.");
                return null; // Indique que la connexion a échoué
            }

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

                    users.add(new Employee(id, name, lastName, mail, password, isAdmin, isActive));
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

    public static void main(String[] args) {
        ArrayList<Employee> users = dataFromDB();
        if (users != null) {
            for (Employee user : users) {
                System.out.println(user.getName() + " " + user.getLastName() + " " + user.getMail() + " " + user.getPassword() + " " + user.isAdmin() + " " + user.isActive());
            }
        } else {
            System.out.println("Aucun utilisateur trouvé.");
        }
    }
}
