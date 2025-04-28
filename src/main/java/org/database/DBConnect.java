package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521/ORCLPDB";
	private static final String USER = "hotel_user";
	private static final String PASSWORD = "2426";
	
	// Creates and returns a connection to the database
	public static Connection connect() throws SQLException {
		Connection connection = null;
		connection = DriverManager.getConnection(URL, USER, PASSWORD);
		System.out.println("Connexion réussie à Oracle !");
		return connection;
	}

	// public static void main(String[] args) {
	// System.out.println(connect());
	// }
}
