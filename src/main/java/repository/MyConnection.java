package repository;

import eceptions.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static Connection connection ;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","sina1717");
        } catch (SQLException e) {
            throw new ConnectionException("can not connect to data base");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
