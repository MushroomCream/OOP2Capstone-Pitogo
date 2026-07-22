package com.example.capstonepitogo.GymMembership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GymDatabaseFacade {

    public boolean processPayment(int userId, double amount) {
        String query = "INSERT INTO payments (user_id, amount) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setDouble(2, amount);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}