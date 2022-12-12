package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.PaymentCellFactory;
import com.splitwiser.splitwiserclient.mockData.MockDataProvider;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class SummaryController {

    @FXML
    private Label userInvolvedLabel;

    @FXML
    private ListView<Payment> otherPaymentList;

    @FXML
    private Label userInvolvedBalanceLabel;

    @FXML
    private Label userInvolvedBalanceValueLabel;
    @FXML
    private ListView<Payment> userInvolvedPaymentsList;
    private AppController appController;

    private ObjectProperty<User> currentUser;

    private SimpleObjectProperty<BigDecimal> currentUsersBalance;

    @FXML
    private void initialize(){
        this.currentUser = new SimpleObjectProperty<User>();
        this.currentUsersBalance = new SimpleObjectProperty<BigDecimal>(BigDecimal.valueOf(0));

        this.otherPaymentList.setCellFactory(new PaymentCellFactory());
        this.userInvolvedPaymentsList.setCellFactory(new PaymentCellFactory());
    }

    @FXML
    protected void onCreatePaymentButtonClick() {
        Payment newPayment = new Payment(this.currentUser.get().getGroup(), BigDecimal.valueOf(0), new Date(1, Calendar.JANUARY, 1900), "", this.currentUser.get());
        boolean isCreated = appController.showCreatePaymentDialog(newPayment);
        if (isCreated){
            //probably necessary to check if user is really involved
            this.userInvolvedPaymentsList.getItems().add(newPayment);
            MockDataProvider.addPayment(newPayment);
        }
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
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
        this.userInvolvedLabel.setText("with "+ this.currentUser.get().getFirstName() + " :");
        this.userInvolvedBalanceLabel.setText(this.currentUser.get().getFirstName() + " " + this.currentUser.get().getLastName() + " balance: ");
    }

    public void setPaymentsList(){
        ObservableList<Payment> allPayments = MockDataProvider.getMockPayments();
        ObservableList<Payment> userInvolvedPayments = MockDataProvider.getAllUserInvolvedPayments(this.currentUser.get(), allPayments);
        userInvolvedPaymentsList.setItems(userInvolvedPayments);
        otherPaymentList.setItems(allPayments.filtered((payment) -> !userInvolvedPayments.contains(payment)));


        this.currentUsersBalance = new SimpleObjectProperty<>(MockDataProvider.getUsersBalance(this.currentUser.get(), this.userInvolvedPaymentsList.getItems()));
        this.userInvolvedBalanceValueLabel.setText(this.currentUsersBalance.get().toString());
    }
}
