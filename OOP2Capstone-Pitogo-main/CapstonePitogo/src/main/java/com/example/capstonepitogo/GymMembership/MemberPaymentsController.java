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

public class MemberPaymentsController {
    @FXML private TableView<PaymentRecord> paymentsTable;
    @FXML private TableColumn<PaymentRecord, Double> amountColumn;
    @FXML private TableColumn<PaymentRecord, String> dateColumn;

    @FXML
    public void initialize() {
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        loadMyPayments();
    }

    private void loadMyPayments() {
        int currentUserId = UserSession.getLoggedInUserId();
        ObservableList<PaymentRecord> list = FXCollections.observableArrayList();

        // Only fetch payments belonging to the currently logged-in user
        String query = "SELECT amount, payment_date FROM payments WHERE user_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, currentUserId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new PaymentRecord(rs.getDouble("amount"), rs.getString("payment_date")));
            }
            paymentsTable.setItems(list);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("memberdashboard-view.fxml"))));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static class PaymentRecord {
        private final double amount;
        private final String paymentDate;

        public PaymentRecord(double amount, String paymentDate) {
            this.amount = amount;
            this.paymentDate = paymentDate;
        }

        public double getAmount() { return amount; }
        public String getPaymentDate() { return paymentDate; }
    }
}