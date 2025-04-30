package org.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Controller Singleton, manages data control and access to database.
 */
// implimentation de la classe Controller
// qui est un singleton et qui gère l'accès à la base de données et la gestion des données

public class Controller {

	private static Controller INSTANCE;
	private Connection connection = null;
	private UserManager userManager = new UserManager();
	private int currentUser = -1; // user id de l'utilisateur connecté
	private RoomManager roomManager = new RoomManager();
	private ReservationManager reservationManager = new ReservationManager();
	private ClientManager clientManager = new ClientManager();

	private Controller() {
	}

	public static synchronized Controller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller();
		}
		return INSTANCE;
	}

	/**
	 * Attempts to establish a connection to the given database URL.
	 * The {@code connection} is set to null if the method fails.
	 */
	public void initializeConnection(String url, String user, String password) throws SQLException {
		connection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * Closes the connection if it's open.
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	// getters
	public Connection getConnection() {
		return connection;
	}

	public void setCurrentUser(int currentUser) {
		this.currentUser = currentUser;
	}

	public int getCurrentUser() {
		return currentUser;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public RoomManager getRoomManager() {
		return roomManager;
	}

	public ReservationManager getReservationManager() {
		return reservationManager;
	}

	public ClientManager getClientManager() {
		return clientManager;
	}
}
