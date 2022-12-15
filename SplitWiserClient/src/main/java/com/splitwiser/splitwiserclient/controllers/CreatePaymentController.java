package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.auxiliary.UserCellFactory;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CreatePaymentController {

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

    @FXML
    private void initialize() {
        this.isApproved = false;

        this.payerListPicker.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.payerListPicker.setCellFactory(new UserCellFactory());

        this.receiverListPicker.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.receiverListPicker.setCellFactory(new UserCellFactory());

        this.createPaymentButton.disableProperty().bind(Bindings.equal("0", this.valueTextField.textProperty()));
    }

    @FXML
    private void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    private void handleCreateAction(ActionEvent actionEvent) {
        this.updateModel();
        this.isApproved = true;
        this.dialogStage.close();
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
        //binding the user with list of payers (?)
        this.descriptionTextField.setText(payment.getDescription());
        this.valueTextField.setText(payment.getValue().toString());
        this.datePicker.setValue(payment.getDate());

        this.payerListPicker.setItems(payment.getPayer().getGroup().getMembers());
        this.receiverListPicker.setItems(payment.getPayer().getGroup().getMembers());

    }

    private void updateModel() {
        this.payment.setDescription(this.descriptionTextField.getText());
        this.payment.setDate(this.datePicker.getValue());

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
