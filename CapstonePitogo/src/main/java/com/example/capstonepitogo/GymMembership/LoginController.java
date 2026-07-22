package com.example.capstonepitogo.GymMembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // ADD 'id' TO YOUR SELECT QUERY:
        String query = "SELECT id, password, role FROM users WHERE username = ?";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // GRAB THE ID FROM THE DATABASE:
                int userId = rs.getInt("id");
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");

                if (password.equals(dbPassword)) {

                    // SAVE THE ID INTO THE SESSION:
                    UserSession.createSession(userId, role);

                    if ("STAFF".equals(role)) {
                        loadScene("staffdashboard-view.fxml");
                    } else {
                        loadScene("memberdashboard-view.fxml");
                    }

                } else {
                    messageLabel.setText("Invalid password.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                messageLabel.setText("User not found.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Database connection error.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        loadScene("welcome-view.fxml");
    }

    private void loadScene(String fxmlFile) {
        try {
            URL resource = getClass().getResource(fxmlFile);

            if (resource == null) {
                System.err.println("Error: Cannot find FXML file -> " + fxmlFile);
                return;
            }

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(resource));

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Failed to load scene: " + e.getMessage());
            e.printStackTrace();
        }
    }
}