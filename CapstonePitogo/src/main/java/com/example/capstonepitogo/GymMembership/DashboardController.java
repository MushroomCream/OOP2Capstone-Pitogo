package com.example.capstonepitogo.GymMembership;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label membersLabel;

    @FXML
    private Label membershipLabel;

    @FXML
    private Label attendanceLabel;

    @FXML
    private Label revenueLabel;

    @FXML
    public void initialize() {

        membersLabel.setText("6767");
        membershipLabel.setText("676767");
        attendanceLabel.setText("67");
        revenueLabel.setText("$67");
    }

    @FXML
    private void openMembers() {
        System.out.println("Members clicked");
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

    @FXML
    private void handleLogout() {
        System.out.println("Logout clicked");
    }

}