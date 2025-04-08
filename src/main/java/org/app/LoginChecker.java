package org.app;

import java.sql.*;
import org.database.DBConnect;

public class LoginChecker {
    public static boolean loginCheck() {
        Connection connection = DBConnect.connect();
        System.err.println("Connection: " + connection);
        if (connection != null) {
            String sql = "{ call check_login(?, ?, ?) }";
            try {
                CallableStatement stmt = connection.prepareCall(sql);
                stmt.setString(1, "med@gmail.com");
                stmt.setString(2, "1234");
                stmt.registerOutParameter(3, Types.INTEGER);

                stmt.execute();
                int result = stmt.getInt(3);
                if (result == 1) {
                    System.out.println("User exists.");
                    return true;
                } else {
                    System.out.println("User does not exist.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }
}
