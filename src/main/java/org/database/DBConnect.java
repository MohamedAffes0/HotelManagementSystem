package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/ORCLPDB";
    private static final String USER = "hotel_user";
    private static final String PASSWORD = "2426";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à Oracle !");
        } catch (SQLException e) {
            System.out.println("Échec de la connexion.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        connect();
    }
}
