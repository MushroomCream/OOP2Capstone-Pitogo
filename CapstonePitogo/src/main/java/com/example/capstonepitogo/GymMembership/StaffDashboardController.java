package com.example.capstonepitogo.GymMembership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDashboardController {

    @FXML private TableView<Member> membersTable;
    @FXML private TableColumn<Member, Integer> idColumn;
    @FXML private TableColumn<Member, String> fullNameColumn;
    @FXML private TableColumn<Member, String> usernameColumn;

    @FXML private TextField newFullNameField;
    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        loadMembers();
    }

    @FXML
    private void openPlans() {
        System.out.println("Plans clicked");
    }

    @FXML
    private void openAttendance() {
        System.out.println("Attendance clicked");
    }

    @FXML
    private void openPayments() {
        System.out.println("Payments clicked");
    }

    private void loadMembers() {
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String query = "SELECT id, full_name, username FROM users WHERE role = 'MEMBER'";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                memberList.add(new Member(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("username")
                ));
            }

            membersTable.setItems(memberList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddMember() {
        String fullName = newFullNameField.getText();
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields to add a member.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String insertQuery = "INSERT INTO users (username, password, role, full_name) VALUES (?, ?, 'MEMBER', ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                messageLabel.setText("Member added successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");

                newFullNameField.clear();
                newUsernameField.clear();
                newPasswordField.clear();

                loadMembers();
            }

        } catch (SQLException e) {
            messageLabel.setText("Error: Username might already be taken.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();

        if (selectedMember == null) {
            messageLabel.setText("Please select a member from the table to remove.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

            pstmt.setInt(1, selectedMember.getId());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                messageLabel.setText("Member removed successfully.");
                messageLabel.setStyle("-fx-text-fill: green;");
                loadMembers();
            }

        } catch (SQLException e) {
            messageLabel.setText("Database error while removing member.");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
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

    public static class Member {
        private final int id;
        private final String fullName;
        private final String username;

        public Member(int id, String fullName, String username) {
            this.id = id;
            this.fullName = fullName;
            this.username = username;
        }

        public int getId() { return id; }
        public String getFullName() { return fullName; }
        public String getUsername() { return username; }
    }
}