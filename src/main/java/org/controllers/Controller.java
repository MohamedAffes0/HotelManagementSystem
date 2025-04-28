package org.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.controllers.client.ClientManager;
import org.controllers.reservation.ReservationManager;
import org.controllers.room.RoomManager;
import org.controllers.user.UserManager;

/**
 * Controller Singleton, manages data control and access to database.
 */
public class Controller {

	private static Controller INSTANCE;
	private Connection connection = null;
	private UserManager userManager = new UserManager();
	private RoomManager roomManager = new RoomManager();
	private ReservationManager reservationManager = new ReservationManager();
	private ClientManager clientManager = new ClientManager();

	private Controller() {
	}

	// Synchronized is added here to ensure thread safety.
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
