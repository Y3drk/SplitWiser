package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.libs.graphs.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    private Stage primaryStage;

    private DataProvider dataProvider;

    public AppController(Stage primaryStage, DataProvider dataProvider) {
        this.primaryStage = primaryStage;
        this.dataProvider = dataProvider;
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    private FXMLLoader getLoaderWithLocation(String resource) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AppController.class.getResource(resource));
        return loader;
    }

    private Stage createDialogStage(BorderPane page, String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(this.primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    private void setSceneAndShow(BorderPane layout) {
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void initLoginLayout() {
        try {
            this.primaryStage.setTitle("SplitWiser Client Application");

            FXMLLoader loader = this.getLoaderWithLocation("login-view.fxml");
            BorderPane loginLayout = loader.load();

            // set initial data into controller
            LoginController controller = loader.getController();
            controller.setDataProvider(this.dataProvider);
            controller.setAppController(this);
            controller.initData();

            this.setSceneAndShow(loginLayout);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initSummaryLayout(User currentlyLoggedUser) {
        try {

            primaryStage.setTitle(currentlyLoggedUser.getFirstName() + " " + currentlyLoggedUser.getLastName() + "; " + currentlyLoggedUser.getGroup().getName() + " summary");

            FXMLLoader loader = this.getLoaderWithLocation("summary-view.fxml");
            BorderPane summaryLayout = loader.load();

            // set initial data into controller
            SummaryController controller = loader.getController();
            controller.setAppController(this);
            controller.setCurrentUser(currentlyLoggedUser);
            controller.setDataProvider(this.dataProvider);
            controller.initData();

            this.setSceneAndShow(summaryLayout);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showCreateUserDialog(User newUser) {
        try {
            FXMLLoader loader = this.getLoaderWithLocation("create-user-dialog.fxml");
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = this.createDialogStage(page, "Create User");

            CreateUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDataProvider(this.dataProvider);
            controller.setUser(newUser);
            controller.initData();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApproved();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showCreateGroupDialog(Group group) {
        try {
            FXMLLoader loader = getLoaderWithLocation("create-group-dialog.fxml");
            BorderPane page = loader.load();

            Stage dialogStage = this.createDialogStage(page, "Create Group");

            // Set the group into the controller.
            CreateGroupController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDataProvider(this.dataProvider);
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
            FXMLLoader loader = this.getLoaderWithLocation("create-payment-dialog.fxml");
            BorderPane page = loader.load();

            Stage dialogStage = this.createDialogStage(page, "Create Payment");

            // Set the group into the controller.
            CreatePaymentController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDataProvider(this.dataProvider);
            controller.setPayment(payment);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isApproved();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showViewGraphDialog(String name, boolean isAggregated, SmartGraphPanel<String, String> graphView) {
        try {
            FXMLLoader loader = this.getLoaderWithLocation("view-graph-dialog.fxml");
            BorderPane page = loader.load();

            Stage dialogStage = this.createDialogStage(page, name);

            // Set the group into the controller.
            ViewGraphController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGraphView(graphView);
            if (isAggregated){
                String AGGREGATED_PAYMENTS_GRAPH_DESCRIPTION = "Arrow represents the aggregated amount of money that someone owes the other person. It points towards the borrower.";
                controller.setDescriptionLabel(AGGREGATED_PAYMENTS_GRAPH_DESCRIPTION);
            }
           else {
                String ALL_PAYMENTS_GRAPH_DESCRIPTION = "Arrow represents the amount of money that someone paid for the other person. It points towards the receiver";
                controller.setDescriptionLabel(ALL_PAYMENTS_GRAPH_DESCRIPTION);
            }

            dialogStage.show();

            graphView.init();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}