package com.splitwiser.splitwiserclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    private AppController appController;
    @FXML
    private Label welcomeText;

    @FXML
    private void initialize(){

    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onCreateuserButtonClick(ActionEvent actionEvent) {
        appController.showCreateUserDialog();
    }
    public void setAppController(AppController appController) {
        this.appController = appController;
    }


}
