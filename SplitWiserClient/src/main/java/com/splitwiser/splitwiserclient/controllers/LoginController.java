package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.mockData.MockDataProvider;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginController {

    private AppController appController;

    @FXML
    private ListView<User> usersList; //is list view better than dynamically spawning buttons? -> prolly yes


    @FXML
    private void initialize() {

    }

    @FXML
    protected void onHelloButtonClick() {
        this.appController.initSummaryLayout();
    }

    @FXML
    public void onCreateUserButtonClick(ActionEvent actionEvent) {
        appController.showCreateUserDialog();
    }

    @FXML
    public void onCreateGroupButtonClick(ActionEvent actionEvent) {
        appController.showCreateGroupDialog();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setUsersList(){
        usersList.setItems(MockDataProvider.getMockUsers());
    }
}
