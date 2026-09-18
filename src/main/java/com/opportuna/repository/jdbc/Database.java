package com.opportuna.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database() {
    }

    public static Connection connect() throws SQLException {
        String url = System.getenv().getOrDefault(
                "OPPORTUNA_DB_URL",
                "jdbc:mysql://localhost:3306/opportuna?useSSL=false&serverTimezone=UTC");
        String user = System.getenv().getOrDefault("OPPORTUNA_DB_USER", "root");
        String password = System.getenv().getOrDefault("OPPORTUNA_DB_PASSWORD", "");

        return DriverManager.getConnection(url, user, password);
    }
}
