package com.revature.repository;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection createConnection() throws SQLException {
        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        String url = "jdbc:postgresql://localhost:5433/postgres";

        String username = "postgres";
        String password = "password";

        return DriverManager.getConnection(url, username, password);
    }
}
