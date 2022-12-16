package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.group.Group;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateGroupController {
    @FXML
    public TextField groupNameTextField;
    @FXML
    public Button createGroupButton;
    private Stage dialogStage;

    private Group group;

    private boolean isApproved;

    private DataProvider dataProvider = DataProvider.getInstance();

    @FXML
    private void initialize() {
        this.isApproved = false;
        this.createGroupButton.disableProperty().bind(Bindings.isEmpty(this.groupNameTextField.textProperty()));
    }

    public void setGroup(Group group) {
        this.group = group;
        updateControls();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    private void handleCreateAction(ActionEvent actionEvent) {
        this.updateModel();
        this.dataProvider.addGroup(this.group).first(this.group).blockingSubscribe(newGroup -> {
            this.group = newGroup;
        });
        isApproved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    private void updateControls() {
        groupNameTextField.setText(this.group.getName());
    }

    private void updateModel() {
        this.group.setName(groupNameTextField.getText());
    }

    public boolean isApproved() {
        return isApproved;
    }
}
