package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    private Stage primaryStage ;

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

    public void initSummaryLayout(User currentlyLoggedUser) {
        try {

            primaryStage.setTitle(currentlyLoggedUser.getFirstName() + " " + currentlyLoggedUser.getLastName() + "; " + currentlyLoggedUser.getGroup().getName() + " summary");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("summary-view.fxml"));
            BorderPane summaryLayout = loader.load();

            // set initial data into controller
            SummaryController controller = loader.getController();
            controller.setAppController(this);
            controller.setCurrentUser(currentlyLoggedUser);
            controller.setPaymentsList();


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

    public boolean showCreatePaymentDialog(Payment payment) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppController.class.getResource("create-payment-dialog.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Payment");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the group into the controller.
            CreatePaymentController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPayment(payment);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApproved();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}