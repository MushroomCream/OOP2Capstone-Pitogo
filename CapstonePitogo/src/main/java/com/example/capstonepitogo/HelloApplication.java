package com.example.capstonepitogo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GymMembership/welcome-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Capstone-Pitogo");
        stage.setScene(scene);
        stage.show();

        // July 17 > need to implement membership-plans table and attendance table

    }
}