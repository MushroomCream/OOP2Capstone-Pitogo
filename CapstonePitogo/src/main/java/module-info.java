module com.example.capstonepitogo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.capstonepitogo to javafx.fxml;
    exports com.example.capstonepitogo;

    exports com.example.capstonepitogo.GymMembership;
    opens com.example.capstonepitogo.GymMembership to javafx.fxml;

}