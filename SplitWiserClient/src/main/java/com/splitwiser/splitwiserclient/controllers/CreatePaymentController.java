package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.CategoryCellFactory;
import com.splitwiser.splitwiserclient.auxiliary.UserCellFactory;
import com.splitwiser.splitwiserclient.data.DataProvider;
import com.splitwiser.splitwiserclient.model.category.Category;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.AlertGenerator;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePaymentController {

    @FXML
    private ListView<Category> categoryListPicker;
    @FXML
    private RadioButton groupReceiverButton;
    @FXML
    private ListView<User> receiverListPicker;
    @FXML
    private ListView<User> payerListPicker;
    @FXML
    private Button createPaymentButton;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField valueTextField;
    private Stage dialogStage;

    private Payment payment;

    private boolean isApproved;

    private DataProvider dataProvider;

    @FXML
    private void initialize() {
        this.isApproved = false;

        this.payerListPicker.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.payerListPicker.setCellFactory(new UserCellFactory());

        this.receiverListPicker.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.receiverListPicker.setCellFactory(new UserCellFactory());

        this.categoryListPicker.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.categoryListPicker.setCellFactory(new CategoryCellFactory());

        this.createPaymentButton.disableProperty().bind(Bindings.equal("0", this.valueTextField.textProperty()));
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @FXML
    private void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    private void handleCreateAction(ActionEvent actionEvent) {
        if (this.isNewPaymentSinglePerson()
                && this.receiverListPicker.getSelectionModel().getSelectedItem().getId() == this.getPayerId()) {
            AlertGenerator.showErrorAlert("The payer cannot be the same as the receiver!");
            return;
        }
        this.updateModel();

        this.dataProvider.addPayment(this.payment, this.payment.getGroup().getId()).blockingSubscribe(newPayment -> {
            this.payment = newPayment;
            AlertGenerator.showConfirmationAlert("Payment created!");
        }, throwable -> AlertGenerator.showErrorAlert("Could not create payment -> " + throwable.getMessage()));
        this.dataProvider.refetchData();
        this.isApproved = true;
        this.dialogStage.close();
    }

    private boolean isNewPaymentSinglePerson() {
        return this.receiverListPicker.getSelectionModel().getSelectedItem() != null && !this.groupReceiverButton.isSelected();
    }

    private int getPayerId() {
        if (this.payerListPicker.getSelectionModel().getSelectedItem() != null) {
            return this.payerListPicker.getSelectionModel().getSelectedItem().getId();
        }
        return this.payment.getPayer().getId();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        this.updateControls();
    }

    public boolean isApproved() {
        return isApproved;
    }

    private void updateControls() {
        this.descriptionTextField.setText(payment.getDescription());
        this.valueTextField.setText(payment.getAmount().toString());
        this.datePicker.setValue(payment.getDate());

        this.payerListPicker.setItems(payment.getPayer().getGroup().getMembers());
        this.receiverListPicker.setItems(payment.getPayer().getGroup().getMembers());

        ObservableList<Category> categories = FXCollections.observableArrayList();
        categories.addAll(Arrays.stream(Category.values()).toList());
        this.categoryListPicker.setItems(categories);

    }

    private void updateModel() {
        this.payment.setDescription(this.descriptionTextField.getText());
        this.payment.setDate(this.datePicker.getValue());
        this.payment.setCategory(this.categoryListPicker.getSelectionModel().getSelectedItem());

        // sets to selected payer only if selected
        if (!this.payerListPicker.getSelectionModel().getSelectedItems().isEmpty()) {
            this.payment.setPayer(this.payerListPicker.getSelectionModel().getSelectedItem());
        }

        if (!this.receiverListPicker.getSelectionModel().getSelectedItems().isEmpty() && !this.groupReceiverButton.isSelected()) {
            List<User> receiver = new ArrayList<>();
            receiver.add(this.receiverListPicker.getSelectionModel().getSelectedItem());
            this.payment.setReceivers(receiver);
        }

        DecimalFormat decimalFormatter = new DecimalFormat();
        decimalFormatter.setParseBigDecimal(true);
        try {
            this.payment.setAmount((BigDecimal) decimalFormatter.parse(valueTextField.getText()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
