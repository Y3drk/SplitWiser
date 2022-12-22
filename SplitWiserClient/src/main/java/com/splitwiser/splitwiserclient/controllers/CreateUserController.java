package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.GroupCellFactory;
import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.AlertGenerator;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
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

    private DataProvider dataProvider;

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

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
        this.dataProvider.addUser(this.user).first(this.user).blockingSubscribe(newUser -> {
            this.user = newUser;
            AlertGenerator.showConfirmationAlert(user.getFirstName() + " " + user.getLastName() + " was created successfully");
        }, throwable -> AlertGenerator.showErrorAlert("Could not create new user -> " + throwable.getMessage()));
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

    public void initData() {
        this.dataProvider.refetchData();
        setGroupsList(this.dataProvider.getGroupsData());
    }
}
