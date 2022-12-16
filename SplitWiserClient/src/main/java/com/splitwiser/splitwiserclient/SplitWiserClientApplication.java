package com.splitwiser.splitwiserclient;

import com.splitwiser.splitwiserclient.controllers.AppController;
import com.splitwiser.splitwiserclient.data.DataProvider;
import javafx.application.Application;
import javafx.stage.Stage;

public class SplitWiserClientApplication extends Application {
    private Stage primaryStage;

    private AppController appController;

    @Override
    public void start(Stage stage){
        this.primaryStage = stage;

        DataProvider dataProvider = DataProvider.getInstance();
        dataProvider.init();

        this.appController = new AppController(primaryStage);
        this.appController.initLoginLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }
}