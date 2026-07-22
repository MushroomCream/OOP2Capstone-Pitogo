package com.example.capstonepitogo.GymMembership;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/gymmembership";
    public static final String USER = "root";
    public static final String PASS = "";

    private static MySQLConnection instance;

    private MySQLConnection() {
    }

    public static MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            if (conn != null) {
                System.out.println("Database connected successfully via Singleton!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}