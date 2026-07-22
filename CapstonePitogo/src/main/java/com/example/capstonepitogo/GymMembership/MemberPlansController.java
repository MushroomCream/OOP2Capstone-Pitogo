package com.example.capstonepitogo.GymMembership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberPlansController {
    @FXML private TableView<PlansController.Plan> plansTable;
    @FXML private TableColumn<PlansController.Plan, String> nameColumn;
    @FXML private TableColumn<PlansController.Plan, Double> priceColumn;
    @FXML private TableColumn<PlansController.Plan, Integer> durationColumn;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("planName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationMonths"));
        loadPlans();
    }

    private void loadPlans() {
        ObservableList<PlansController.Plan> list = FXCollections.observableArrayList();
        String query = "SELECT id, plan_name, price, duration_months FROM membership_plans";
        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PlansController.Plan(
                        rs.getInt("id"),
                        rs.getString("plan_name"),
                        rs.getDouble("price"),
                        rs.getInt("duration_months")
                ));
            }
            plansTable.setItems(list);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleSelectPlan(ActionEvent event) {
        PlansController.Plan selectedPlan = plansTable.getSelectionModel().getSelectedItem();

        if (selectedPlan == null) {
            messageLabel.setText("Please select a plan first!");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int userId = UserSession.getLoggedInUserId();
        double basePrice = selectedPlan.getPrice();

        // BEHAVIORAL PATTERN: Choose the pricing strategy dynamically
        // You could later base this on a checkbox or user profile data
        PricingStrategy pricingStrategy = new RegularPricing(); // Or new StudentDiscountPricing();
        double finalAmount = pricingStrategy.calculatePrice(basePrice);

        // STRUCTURAL PATTERN: Use the Facade to save to the database
        GymDatabaseFacade dbFacade = new GymDatabaseFacade();
        boolean success = dbFacade.processPayment(userId, finalAmount);

        if (success) {
            messageLabel.setText("Successfully subscribed for $" + String.format("%.2f", finalAmount) + "!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Failed to process payment.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("memberdashboard-view.fxml"))));
        } catch (IOException e) { e.printStackTrace(); }
    }
}