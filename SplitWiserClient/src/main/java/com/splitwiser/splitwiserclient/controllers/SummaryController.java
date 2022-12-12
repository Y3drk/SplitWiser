package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.PaymentCellFactory;
import com.splitwiser.splitwiserclient.mockData.MockDataProvider;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class SummaryController {

    @FXML
    private ListView<String> totalSummaryList;
    @FXML
    private Label groupLabel;
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

    private ObservableList<Payment> allPayments = FXCollections.observableArrayList();
    private AppController appController;

    private ObjectProperty<User> currentUser;

    private SimpleObjectProperty<BigDecimal> currentUsersBalance;

    @FXML
    private void initialize(){
        this.currentUser = new SimpleObjectProperty<User>();
        this.currentUsersBalance = new SimpleObjectProperty<BigDecimal>(BigDecimal.valueOf(0));

        this.otherPaymentList.setCellFactory(new PaymentCellFactory());
        this.userInvolvedPaymentsList.setCellFactory(new PaymentCellFactory());

        this.totalSummaryList.setFocusTraversable(false);

    }

    @FXML
    protected void onCreatePaymentButtonClick() {
        Payment newPayment = new Payment(this.currentUser.get().getGroup(), BigDecimal.valueOf(0), LocalDate.of(2022, 1, 1), "", this.currentUser.get());
        boolean isCreated = appController.showCreatePaymentDialog(newPayment);
        if (isCreated){
            this.allPayments.add(newPayment);
            MockDataProvider.addPayment(newPayment);

            if (newPayment.getPayer().equals(this.currentUser.get()) || newPayment.getReceiver() == null || newPayment.getReceiver().equals(this.currentUser.get())){
                this.userInvolvedPaymentsList.getItems().add(newPayment);
                this.currentUsersBalance = new SimpleObjectProperty<>(MockDataProvider.getUsersBalance(this.currentUser.get(), this.userInvolvedPaymentsList.getItems()));
                this.userInvolvedBalanceValueLabel.setText(this.currentUsersBalance.get().toString());
            }

            totalSummaryList.setItems(MockDataProvider.calculateBalanceBetweenAll(this.currentUser.get(), allPayments));
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
        this.groupLabel.setText(this.currentUser.get().getGroup().getName() + " balance");
    }

    public void setPaymentsList(){
        allPayments.addAll(MockDataProvider.getMockPayments().filtered((elem) -> elem.getGroup() == this.currentUser.get().getGroup()));
        ObservableList<Payment> userInvolvedPayments = MockDataProvider.getAllUserInvolvedPayments(this.currentUser.get(), allPayments);
        userInvolvedPaymentsList.setItems(userInvolvedPayments);
        otherPaymentList.setItems(allPayments.filtered((payment) -> !userInvolvedPayments.contains(payment)));
        totalSummaryList.setItems(MockDataProvider.calculateBalanceBetweenAll(this.currentUser.get(), allPayments));

        this.currentUsersBalance = new SimpleObjectProperty<>(MockDataProvider.getUsersBalance(this.currentUser.get(), this.userInvolvedPaymentsList.getItems()));
        this.userInvolvedBalanceValueLabel.setText(this.currentUsersBalance.get().toString());
    }
}
