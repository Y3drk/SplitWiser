package com.splitwiser.splitwiserclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SummaryController {
    private AppController appController;

    @FXML
    private Label welcomeText;

    @FXML
    private void initialize(){

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Summary is here!");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        //this is probably inefficient, those controllers could be singletons!
        this.appController.initLoginLayout();
    }
}
