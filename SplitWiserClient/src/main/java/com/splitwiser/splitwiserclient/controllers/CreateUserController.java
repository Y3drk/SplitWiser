package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.GroupCellFactory;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateUserController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private ListView<Group> groupsList;
    @FXML
    private Button createUserButton;
    private Stage dialogStage;

    private User user;

    private boolean isApproved;

    @FXML
    private void initialize() {
        this.isApproved = false;
        this.groupsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.groupsList.setCellFactory(new GroupCellFactory());

        this.createUserButton.disableProperty().bind(Bindings.isEmpty(this.firstNameTextField.textProperty())
                .or(Bindings.isEmpty(this.lastNameTextField.textProperty()))
                .or(Bindings.isEmpty(this.groupsList.getSelectionModel().getSelectedItems())));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setGroupsList(ObservableList<Group> groupsList) {
        this.groupsList.setItems(groupsList);
    }

    public void setUser(User user) {
        this.user = user;
        this.updateControls();
    }

    @FXML
    private void handleCreateAction(ActionEvent actionEvent) {
        this.updateModel();
        isApproved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    private void updateControls() {
        this.firstNameTextField.setText(user.getFirstName());
        this.lastNameTextField.setText(user.getLastName());
    }

    private void updateModel() {
        this.user.setFirstName(firstNameTextField.getText());
        this.user.setLastName(lastNameTextField.getText());
        this.user.setGroup(this.groupsList.getSelectionModel().getSelectedItem());
    }
}
