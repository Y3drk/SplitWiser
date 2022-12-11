package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SummaryController {
    private AppController appController;

    private ObjectProperty<User> currentUser;

    @FXML
    private Label welcomeText;

    @FXML
    private void initialize(){
        this.currentUser = new SimpleObjectProperty<User>();
    }

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome " + this.currentUser.get().getFirstName() + " " + this.currentUser.get().getLastName());
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        this.appController.initLoginLayout();
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
    }
}
