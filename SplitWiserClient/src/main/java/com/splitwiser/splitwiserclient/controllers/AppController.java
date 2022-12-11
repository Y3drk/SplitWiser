package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            loader.setLocation(AppController.class.getResource("login-view.fxml"));
            BorderPane loginLayout = loader.load();

            // set initial data into controller
            LoginController controller = loader.getController();
            controller.setAppController(this);
            controller.setUsersList();


            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: (order is important)
    // 1. Set up a draft of client-side model -> done (but it's not picture perfect)
    // 2. Create createUserController and createGroupController -> done (but only simple placeholders)
    // 2.1 prepare createUserController
    // 3. Create fxml files for group and user creation -> done (again simple placeholders)
    // 4. Connect it all -> not there completely


    public void initSummaryLayout(User currentlyLoggedUser) {
        try {

            primaryStage.setTitle(currentlyLoggedUser.getFirstName() + " " + currentlyLoggedUser.getLastName() + "; " + currentlyLoggedUser.getGroup().getName() + " summary");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("summary-view.fxml"));
            VBox summaryLayout = loader.load();

            // set initial data into controller
            SummaryController controller = loader.getController();
            controller.setAppController(this);
            controller.setCurrentUser(currentlyLoggedUser);


            Scene scene = new Scene(summaryLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showCreateUserDialog(User newUser, ObservableList<Group> groups) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("create-user-dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            CreateUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(newUser);
            controller.setGroupsList(groups);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApproved();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showCreateGroupDialog(Group group) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("create-group-dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Group");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the group into the controller.
            CreateGroupController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGroup(group);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApproved();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}