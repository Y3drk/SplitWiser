package com.splitwiser.splitwiserclient;

import com.splitwiser.splitwiserclient.controllers.AppController;
import com.splitwiser.splitwiserclient.data.DataProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SplitWiserClientApplication extends Application {
    private Stage primaryStage;

    private AppController appController;

    @Override
    public void start(Stage stage) {
        DataProvider dataProvider = new DataProvider();
        this.primaryStage = stage;
        stage.getIcons().add(new Image("/assets/coin.png"));
        stage.setTitle("SplitWiser");

        this.appController = new AppController(primaryStage, dataProvider);
        this.appController.initLoginLayout();

    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}