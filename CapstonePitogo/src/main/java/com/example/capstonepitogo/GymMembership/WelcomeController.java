package com.example.capstonepitogo.GymMembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WelcomeController {

    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        System.out.println("Welcome Screen Loaded");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        loadScene("login-view.fxml", event);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        loadScene("register-view.fxml", event);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            URL resource = getClass().getResource(fxmlFile);

            if (resource == null) {
                System.err.println("Error: Cannot find FXML file -> " + fxmlFile);
                showError("Could not find the page: " + fxmlFile);
                return;
            }

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(resource));

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Failed to load scene: " + e.getMessage());
            showError("Failed to load the page. Check console for details.");
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loading Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}