package org.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.models.Employee;

// implimentation de la classe Controller
// qui est un singleton et qui gère l'accès à la base de données et la gestion des données
/**
 * Controller Singleton, manages data control and access to database.
 */
public class Controller {

	private static Controller INSTANCE;
	private Connection connection = null;
	private UserManager userManager = new UserManager();
	private Employee currentUser = null; // user id de l'utilisateur connecté
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
	// initialise la connexion à la base de données
	public void initializeConnection(String url, String user, String password) throws SQLException {
		connection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * Closes the connection if it's open.
	 * 
	 * @throws SQLException
	 */
	// ferme la connexion à la base de données
	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	// getters
	public Connection getConnection() {
		return connection;
	}

	public void setCurrentUser(Employee currentUser) {
		this.currentUser = currentUser;
	}

	public Employee getCurrentUser() {
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
