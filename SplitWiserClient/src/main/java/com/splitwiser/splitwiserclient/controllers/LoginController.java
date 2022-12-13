package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.UserCellFactory;
import com.splitwiser.splitwiserclient.mockData.MockDataProvider;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class LoginController {
    private AppController appController;

    private ObservableList<Group> groups;

    @FXML
    private ListView<User> usersList; //is list view better than dynamically spawning buttons? -> prolly yes

    @FXML
    public Button loginButton;


    @FXML
    private void initialize() {
        usersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        usersList.setCellFactory(new UserCellFactory());

        loginButton.disableProperty().bind(Bindings.isEmpty(usersList.getSelectionModel().getSelectedItems()));

        this.groups = FXCollections.observableArrayList();
    }

    @FXML
    protected void onLoginButtonClick() {
        User selectedUser = usersList.getSelectionModel().getSelectedItem();
        this.appController.initSummaryLayout(selectedUser);
    }

    @FXML
    public void onCreateUserButtonClick(ActionEvent actionEvent) {
        User newUser = new User("", "", null);
        boolean isCreated = appController.showCreateUserDialog(newUser, this.groups);
        if (isCreated) {
            this.usersList.getItems().add(newUser);
            MockDataProvider.addUser(newUser);
        }
    }

    @FXML
    public void onCreateGroupButtonClick(ActionEvent actionEvent) {
        Group newGroup = new Group("");
        boolean isCreated = appController.showCreateGroupDialog(newGroup);
        if (isCreated) {
            this.groups.add(newGroup);
            MockDataProvider.addGroup(newGroup);
        }

    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setUsersList() {
        usersList.setItems(MockDataProvider.getMockUsers());
        for (User user : usersList.getItems()
        ) {
            Group usersGroup = user.getGroup();
            if (!groups.contains(usersGroup)) {
                groups.add(usersGroup);
            }
        }
    }
}
