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

public class AttendanceController {
    @FXML private TableView<Record> attendanceTable;
    @FXML private TableColumn<Record, Integer> idColumn;
    @FXML private TableColumn<Record, String> memberColumn;
    @FXML private TableColumn<Record, String> timeColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("checkInTime"));
        loadAttendance();
    }

    private void loadAttendance() {
        ObservableList<Record> list = FXCollections.observableArrayList();

        String query = "SELECT a.id, u.full_name, a.check_in_datetime FROM attendance a JOIN users u ON a.user_id = u.id";
        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Record(rs.getInt("id"), rs.getString("full_name"), rs.getString("check_in_datetime")));
            }
            attendanceTable.setItems(list);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("staffdashboard-view.fxml"))));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static class Record {
        private final int id; private final String memberName; private final String checkInTime;
        public Record(int id, String memberName, String checkInTime) {
            this.id = id; this.memberName = memberName; this.checkInTime = checkInTime;
        }
        public int getId() { return id; } public String getMemberName() { return memberName; }
        public String getCheckInTime() { return checkInTime; }
    }
}