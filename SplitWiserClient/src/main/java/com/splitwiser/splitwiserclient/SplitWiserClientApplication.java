package com.splitwiser.splitwiserclient;

import com.splitwiser.splitwiserclient.controllers.AppController;
import com.splitwiser.splitwiserclient.data.DataProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class SplitWiserClientApplication extends Application {
    private Stage primaryStage;

    private AppController appController;

    @Override
    public void start(Stage stage) {
        DataProvider dataProvider = DataProvider.getInstance();
        this.primaryStage = stage;

        this.appController = new AppController(primaryStage);
        this.appController.initLoginLayout();

    }

    @Override
    public void stop() {
        DataProvider.destroy();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}