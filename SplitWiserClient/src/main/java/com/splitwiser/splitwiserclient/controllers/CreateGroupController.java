package com.splitwiser.splitwiserclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateGroupController {
    private Stage dialogStage;

    public Label welcomeText;

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void onHelloButtonClick(ActionEvent actionEvent) {
        this.dialogStage.close();
    }
}
