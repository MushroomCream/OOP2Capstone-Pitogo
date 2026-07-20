package com.example.capstonepitogo.GymMembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberDashboardController {

    @FXML private Label messageLabel;

    @FXML
    private void handleCheckIn(ActionEvent event) {
        int userId = UserSession.getLoggedInUserId();
        String query = "INSERT INTO attendance (user_id) VALUES (?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();

            messageLabel.setText("Checked in successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

        } catch (SQLException e) {
            messageLabel.setText("Error checking in.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {

        UserSession.clearSession();

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login-view.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            URL resource = getClass().getResource(fxmlFile);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(resource));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewPlans(ActionEvent event) {
        loadScene("member-plans-view.fxml", event);
    }

    @FXML
    private void handleViewPayments(ActionEvent event) {
        loadScene("member-payments-view.fxml", event);
    }
}