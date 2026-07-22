package com.example.capstonepitogo.GymMembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("MEMBER", "STAFF");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String role = roleComboBox.getValue();
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (role == null) {
            messageLabel.setText("Please select a role.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String insertQuery = "INSERT INTO users (username, password, role, full_name) VALUES (?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.setString(4, fullName);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                messageLabel.setText("Registration successful! You can now log in.");
                messageLabel.setStyle("-fx-text-fill: green;");

                roleComboBox.setValue(null);
                fullNameField.clear();
                usernameField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
            } else {
                messageLabel.setText("Registration failed. Try again.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (SQLException e) {
            messageLabel.setText("Username already taken or database error.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        loadScene("welcome-view.fxml", event);
    }

    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            URL resource = getClass().getResource(fxmlFile);

            if (resource == null) {
                System.err.println("Error: Cannot find FXML file -> " + fxmlFile);
                return;
            }

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(resource));

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Failed to load scene: " + e.getMessage());
            e.printStackTrace();
        }
    }
}