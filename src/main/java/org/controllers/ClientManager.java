package org.controllers;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.controllers.exceptions.ControllerException;
import org.models.Person;

public class ClientManager extends Manager<Person> {
    public ClientManager() {
		super();
	}

	@Override
	protected Person dataFromResultSet(ResultSet resultSet) throws SQLException {
        int cin = resultSet.getInt("cin");
        String name = resultSet.getString("nom");
        String lastName = resultSet.getString("prenom");
        String mail = resultSet.getString("mail");
		return new Person(cin, name, lastName, mail);
	}

	@Override
	protected String getSelectRequest() {
		return "{ call get_all_users(?) }";
	}

	@Override
	protected CallableStatement getInsertStatement(Person data) throws SQLException {
		CallableStatement stmt;
		String sql = "{ call add_client_hotel(?, ?, ?, ?) }";
		stmt = getConnection().prepareCall(sql);
        stmt.setInt(1, data.getCin());
        stmt.setString(2, data.getName());
        stmt.setString(3, data.getLastName());
        stmt.setString(4, data.getMail());
		return stmt;
	}

	@Override
	protected void insertInputValidation(Person data) throws ControllerException {
		ArrayList<Person> clients = getData();

		// Check existance
		for (Person client : clients) {
			if (client.getCin() == data.getCin()) {
				throw new ControllerException("Ce CIN existe deja.");
			}
		}

		// Verification des champs vides
		if (data.getMail() == null || data.getMail().isEmpty()) {
			throw new ControllerException("L'e-mail est obligatoire.");
		}

		if (data.getName() == null || data.getName().isEmpty()
				|| data.getLastName() == null || data.getLastName().isEmpty()) {
			throw new ControllerException("Veuillez saisir nom et un prenom.");
		}

		// Verifier si l'email est valide
		if (EmailChecker.isValid(data.getMail())) {
			throw new ControllerException("L'e-mail est invalide.");
		}

        // Check cin
        if (data.getCin() <= 9999999 || data.getCin() > 99999999) {
            throw new ControllerException("Le CIN doit être supérieur à 0.");
        }

	}

	@Override
	protected String getDeleteRequest() {
		return "{ call delete_client(?) }";
	}

	@Override
	protected CallableStatement getUpdateStatement(Person data) throws SQLException {
		CallableStatement statement;
		String sql = "{ call modify_client(?, ?, ?, ?) }";
		statement = getConnection().prepareCall(sql);
        statement.setInt(1, data.getCin());
        statement.setString(2, data.getName());
        statement.setString(3, data.getLastName());
        statement.setString(4, data.getMail());
		return statement;
	}

	@Override
    protected void updateInputValidation(Person data) throws ControllerException {

		// Verification des champs vides
		if (data.getMail() == null || data.getMail().isEmpty()) {
			throw new ControllerException("L'e-mail est obligatoire.");
		}

		if (data.getName() == null || data.getName().isEmpty()
				|| data.getLastName() == null || data.getLastName().isEmpty()) {
			throw new ControllerException("Veuillez saisir nom et un prenom.");
		}

		// Verifier si l'email est valide
		if (EmailChecker.isValid(data.getMail())) {
			throw new ControllerException("L'e-mail est invalide.");
		}
	}
}
