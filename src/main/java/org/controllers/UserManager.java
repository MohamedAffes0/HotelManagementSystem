package org.controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.controllers.exceptions.ConnectionUnavailableException;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Employee;

/**
 * UserManager
 */
public class UserManager extends Manager<Employee> {

	public static enum LoginStatus {
		USER_NOT_FOUND,
		INACTIVE_USER,
		ADMIN_USER,
		NORMAL_USER,
	}

	public UserManager() {
		super();
	}

	public LoginStatus checkLogin(String emailText, String passwordText) throws ControllerException {
		Connection connection = getConnection();

		if (connection == null) {
			throw new ConnectionUnavailableException();
		}
		CallableStatement stmt = null;
		// Vérification des champs vides
		if (emailText == null || emailText.isEmpty()) {
			throw new ControllerException("L'email ne doit pas etre vide.");
		}

		// Vérification des champs vides
		if (passwordText == null || passwordText.isEmpty()) {
			throw new ControllerException("Le mot de passe ne doit pas être vide.");
		}

		try {
			String sql = "{ call check_login(?, ?, ?, ?, ?) }";
			stmt = connection.prepareCall(sql);
			stmt.setString(1, emailText);
			stmt.setString(2, passwordText);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.registerOutParameter(4, Types.INTEGER);
			stmt.registerOutParameter(5, Types.INTEGER);

			stmt.execute();
			int result = stmt.getInt(3);
			int isAdmin = stmt.getInt(4);
			int isActive = stmt.getInt(5);

			if (result == 1) {
				System.out.println("Utilisateur existant.");
				if (isActive == 0) {
					return LoginStatus.INACTIVE_USER;
				} else if (isAdmin == 1) {
					return LoginStatus.ADMIN_USER; // Admin
				} else {
					return LoginStatus.NORMAL_USER; // Utilisateur normal
				}
			} else {
				System.out.println("Utilisateur non trouvé.");
				return LoginStatus.USER_NOT_FOUND;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
		} finally {
			// Fermeture des ressources JDBC
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new DBException();
			}
		}
	}

	@Override
	protected Employee dataFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("nom");
		String lastName = resultSet.getString("prenom");
		String mail = resultSet.getString("mail");
		String password = resultSet.getString("mdp");
		boolean isAdmin = resultSet.getInt("is_admin") == 1 ? true : false;
		boolean isActive = resultSet.getInt("is_active") == 1 ? true : false;
		return new Employee(id, name, lastName, mail, password, isAdmin, isActive);
	}

	@Override
	protected String getSelectRequest() {
		return "{ call get_all_users(?) }";
	}

	@Override
	protected CallableStatement getInsertStatement(Employee data) throws SQLException {
		CallableStatement stmt;
		String sql = "{ call add_employe(?, ?, ?, ?, ?, ?) }";
		stmt = getConnection().prepareCall(sql);
		stmt.setString(1, data.getName());
		stmt.setString(2, data.getLastName());
		stmt.setString(3, data.getMail());
		stmt.setString(4, data.getPassword());
		stmt.setInt(5, data.isAdmin() ? 1 : 0);
		stmt.setInt(6, data.isActive() ? 1 : 0);
		return stmt;

	}

	@Override
	protected void insertInputValidation(Employee data) throws ControllerException {
		ArrayList<Employee> employees = getData();

		// Check email
		for (Employee employee : employees) {
			if (employee.getMail() == data.getMail()) {
				throw new ControllerException("Un compte utilisant cet e-mail existe deja.");
			}
		}

		// Verification des champs vides
		if (data.getMail() == null || data.getMail().isEmpty()) {
			throw new ControllerException("L'e-mail est obligatoire.");
		}
		if (data.getPassword() == null || data.getPassword().isEmpty()) {
			throw new ControllerException("Veuillez saisir un mot de passe.");
		}
		if (data.getName() == null || data.getName().isEmpty()
				|| data.getLastName() == null || data.getLastName().isEmpty()) {
			throw new ControllerException("Veuillez saisir nom et un prenom.");
		}

		// Verifier si l'email est valide
		if (EmailChecker.isValid(data.getMail())) {
			throw new ControllerException("L'e-mail est invalide.");
		}

		// Check for existance
		for (Employee employee : employees) {
			if (employee.getId() == data.getId()) {
				throw new ControllerException("Ce CIN existe deja.");
			}
		}
	}

	@Override
	protected String getDeleteRequest() {
		return "{ call delete_user(?) }";
	}

	@Override
	protected CallableStatement getUpdateStatement(Employee data) throws SQLException {
		CallableStatement statement;
		String sql = "{ call modify_user(?, ?, ?) }";
		statement = getConnection().prepareCall(sql);
		statement.setInt(1, data.getId());
		statement.setInt(2, data.isAdmin() ? 1 : 0);
		statement.setInt(3, data.isActive() ? 1 : 0);
		return statement;
	}

	@Override
	protected void updateInputValidation(Employee data) throws ControllerException {
	}
}
