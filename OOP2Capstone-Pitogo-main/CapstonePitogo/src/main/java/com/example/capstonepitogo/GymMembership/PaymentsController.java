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

public class PaymentsController {
    @FXML private TableView<Payment> paymentsTable;
    @FXML private TableColumn<Payment, Integer> idColumn;
    @FXML private TableColumn<Payment, String> memberColumn;
    @FXML private TableColumn<Payment, Double> amountColumn;
    @FXML private TableColumn<Payment, String> dateColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        loadPayments();
    }

    private void loadPayments() {
        ObservableList<Payment> list = FXCollections.observableArrayList();
        String query = "SELECT p.id, u.full_name, p.amount, p.payment_date FROM payments p JOIN users u ON p.user_id = u.id";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Payment(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDouble("amount"),
                        rs.getString("payment_date")
                ));
            }
            paymentsTable.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("staffdashboard-view.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Payment {
        private final int id;
        private final String memberName;
        private final double amount;
        private final String paymentDate;

        public Payment(int id, String memberName, double amount, String paymentDate) {
            this.id = id;
            this.memberName = memberName;
            this.amount = amount;
            this.paymentDate = paymentDate;
        }

        public int getId() { return id; }
        public String getMemberName() { return memberName; }
        public double getAmount() { return amount; }
        public String getPaymentDate() { return paymentDate; }
    }
}