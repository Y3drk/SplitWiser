package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.UserCellFactory;
import com.splitwiser.splitwiserclient.data.DataProvider;
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
    private ListView<User> usersList;

    @FXML
    public Button loginButton;

    private DataProvider dataProvider = DataProvider.getInstance();


    @FXML
    private void initialize() {
        usersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        usersList.setCellFactory(new UserCellFactory());

        loginButton.disableProperty().bind(Bindings.isEmpty(usersList.getSelectionModel().getSelectedItems()));

        this.groups = FXCollections.observableArrayList();
        this.dataProvider.refetchData();
    }

    @FXML
    private void onLoginButtonClick() {
        User selectedUser = usersList.getSelectionModel().getSelectedItem();
        this.appController.initSummaryLayout(selectedUser);
    }

    @FXML
    public void onCreateUserButtonClick(ActionEvent actionEvent) {
        User newUser = new User("", "", null);
        appController.showCreateUserDialog(newUser);
    }

    @FXML
    public void onCreateGroupButtonClick(ActionEvent actionEvent) {
        Group newGroup = new Group("");
        appController.showCreateGroupDialog(newGroup);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void initData() {
        usersList.setItems(dataProvider.getUsersData());
        groups = dataProvider.getGroupsData();
    }
}
