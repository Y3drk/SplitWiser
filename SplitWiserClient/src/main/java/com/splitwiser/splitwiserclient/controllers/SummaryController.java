package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.PaymentCellFactory;
import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.CalculateService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private DataProvider dataProvider;

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @FXML
    private void initialize() {
        this.currentUser = new SimpleObjectProperty<>();
        this.currentUsersBalance = new SimpleObjectProperty<>(BigDecimal.valueOf(0));

        this.otherPaymentList.setCellFactory(new PaymentCellFactory());
        this.userInvolvedPaymentsList.setCellFactory(new PaymentCellFactory());

        this.totalSummaryList.setFocusTraversable(false);

    }

    @FXML
    private void onCreatePaymentButtonClick() {
        Payment newPayment = new Payment(this.currentUser.get().getGroup(), BigDecimal.valueOf(0), LocalDate.of(2022, 1, 1), "", this.currentUser.get(), this.currentUser.get().getGroup().getMembers());
        appController.showCreatePaymentDialog(newPayment);
        initData();
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
        this.userInvolvedLabel.setText("Involving you");
        this.userInvolvedBalanceLabel.setText(this.currentUser.get().getFirstName() + " " + this.currentUser.get().getLastName() + " balance: ");
        this.groupLabel.setText(this.currentUser.get().getGroup().getName() + " balance");
    }

    public void initData() {
        this.dataProvider.refetchData();
        ObservableList<Payment> allPayments = dataProvider.getPaymentsData().filtered((elem) -> elem.getGroup().equals(this.currentUser.get().getGroup()));
        ObservableList<Payment> userInvolvedPayments = dataProvider.getAllUserInvolvedPayments(this.currentUser.get(), allPayments);

        userInvolvedPaymentsList.setItems(userInvolvedPayments);

        otherPaymentList.setItems(allPayments.filtered((payment) -> !userInvolvedPayments.contains(payment)));
        totalSummaryList.setItems(CalculateService.calculateBalanceBetweenAll(this.currentUser.get(), allPayments));

        this.currentUsersBalance = new SimpleObjectProperty<>(CalculateService.calculateUserBalance(this.currentUser.get(), this.userInvolvedPaymentsList.getItems()));
        this.userInvolvedBalanceValueLabel.setText(this.currentUsersBalance.get().toString());
    }

}
