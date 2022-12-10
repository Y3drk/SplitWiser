package com.splitwiser.splitwiserclient;

import com.splitwiser.splitwiserclient.controllers.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SplitWiserClientApplication extends Application {
    private Stage primaryStage;

    private AppController appController;

    @Override
    public void start(Stage stage){
        this.primaryStage = stage;

        this.appController = new AppController(primaryStage);
        this.appController.initLoginLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }
}