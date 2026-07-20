package com.example.capstonepitogo.GymMembership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlansController {
    @FXML private TableView<Plan> plansTable;
    @FXML private TableColumn<Plan, Integer> idColumn;
    @FXML private TableColumn<Plan, String> nameColumn;
    @FXML private TableColumn<Plan, Double> priceColumn;
    @FXML private TableColumn<Plan, Integer> durationColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("planName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationMonths"));
        loadPlans();
    }

    private void loadPlans() {
        ObservableList<Plan> list = FXCollections.observableArrayList();
        String query = "SELECT id, plan_name, price, duration_months FROM membership_plans";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Plan(rs.getInt("id"), rs.getString("plan_name"),
                        rs.getDouble("price"), rs.getInt("duration_months")));
            }
            plansTable.setItems(list);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("staffdashboard-view.fxml"))));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static class Plan {
        private final int id; private final String planName; private final double price; private final int durationMonths;
        public Plan(int id, String planName, double price, int durationMonths) {
            this.id = id; this.planName = planName; this.price = price; this.durationMonths = durationMonths;
        }
        public int getId() { return id; } public String getPlanName() { return planName; }
        public double getPrice() { return price; } public int getDurationMonths() { return durationMonths; }
    }
}