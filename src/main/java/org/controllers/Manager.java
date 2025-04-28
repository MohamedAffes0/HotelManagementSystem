package org.controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.controllers.exceptions.ConnectionUnavailableException;
import org.controllers.exceptions.ControllerException;
import org.controllers.exceptions.DBException;
import org.models.Model;

import oracle.jdbc.OracleTypes;

/**
 * Manager
 */
public abstract class Manager<T extends Model> {
	private ArrayList<T> data = new ArrayList<>();

	protected Manager() {
	}

	public ArrayList<T> getData() {
		return data;
	}

	public void select() throws DBException {
		Connection connection = getConnection();

		if (connection == null) {
			throw new ConnectionUnavailableException();
		}

		data.clear();

		CallableStatement stmt = null;
		try {
			String sql = getSelectRequest();
			stmt = connection.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.execute();

			ResultSet result = null;

			result = (ResultSet) stmt.getObject(1);
			while (result.next()) {
				data.add(dataFromResultSet(result));
			}
			result.close();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
		} finally {
			// Fermeture des ressources JDBC, Ce qui doit toujours s'executer.
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new DBException("Echec durant la liberation des resources.");
			}
		}
	}

	public void insert(T data) throws ControllerException, DBException {
		if (getConnection() == null) {
			throw new ConnectionUnavailableException();
		}

		insertInputValidation(data);

		CallableStatement statement = null;
		try {
			statement = getInsertStatement(data);
			statement.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
		} finally {
			// Fermeture des ressources JDBC
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new DBException();
			}
		}
	}

	public void update(int id, T data) throws ControllerException {
		Connection connection = getConnection();

		updateInputValidation(data);

		if (connection == null) {
			throw new ConnectionUnavailableException();
		}

		boolean exists = false;
		for (T field : this.getData()) {
			if (id == field.getId()) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			throw new ControllerException("Cet identifiant n'existe pas");
		}

		CallableStatement statement = null;
		try {
			statement = getUpdateStatement(data);
			statement.execute();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
		} finally {
			// toujour executer le bloc finally
			// Fermeture des ressources JDBC
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new DBException();
			}
		}
	}

	public void delete(int id) throws DBException {
		Connection connection = getConnection();

		if (connection == null) {
			throw new ConnectionUnavailableException();
		}

		CallableStatement stmt = null;
		try {
			String sql = getDeleteRequest();
			stmt = connection.prepareCall(sql);
			stmt.setInt(1, id);

			stmt.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DBException();
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
				throw new DBException();
			}
		}
	}

	/**
	 * Returns a model from a line of a ResultSet
	 * 
	 * @param resultSet a ResultSet pointing to the desired line.<BR>
	 *                  {@code resultSet.next()} will not be called on it.
	 *
	 */
	protected abstract T dataFromResultSet(ResultSet resultSet) throws SQLException;

	/**
	 * Returns the request for the database which is a call to a plsql procedure
	 * that sets a cursor.
	 */
	protected abstract String getSelectRequest();

	/**
	 * Returns the {@code CallableStatement} used for insert.
	 * The statement will be executed once it is returned from this method.
	 */
	protected abstract CallableStatement getInsertStatement(T data) throws SQLException;

	/**
	 * Returns true if the data is valid or throws an exception if it isn't.
	 */
	protected abstract void insertInputValidation(T data) throws ControllerException;

	/**
	 * Returns the {@code CallableStatement} used for update.
	 * The statement will be executed once it is returned from this method.
	 */
	protected abstract CallableStatement getUpdateStatement(T data) throws SQLException;

	/**
	 * Returns true if the data is valid or throws an exception if it isn't.
	 */
	protected abstract void updateInputValidation(T data) throws ControllerException;

	/**
	 * Returns the request for the database which is a call to a plsql procedure
	 * that takes an integer.
	 */
	protected abstract String getDeleteRequest();

	protected static Connection getConnection() {
		return Controller.getInstance().getConnection();
	}
}
