package com.splitwiser.splitwiserclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    private Stage primaryStage;
    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initLoginLayout() {
        try {
            this.primaryStage.setTitle("SplitWiser Client Application");


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("hello-view.fxml"));
            VBox loginLayout = loader.load();

            // set initial data into controller
            LoginController controller = loader.getController();
            controller.setAppController(this);


            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: (order is important)
    // 1. Set up a draft of client-side model
    // 2. Create createUserController and createGroupController
    // 3. Create fxml files for group and user creation
    // 4. Connect it all

    //done super quickly -> doesn't work
    public boolean showCreateUserDialog() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("hello-view.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the transaction into the presenter.

            // here should be createUserController
            LoginController presenter = loader.getController();
//            presenter.setDialogStage(dialogStage);
//            presenter.setData(transaction);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
//            return presenter.isApproved();
            return true;

        } catch (IOException e) {
            e.printStackTrace(); //this probably should just throw new exception
            return false;
        }
    }
}