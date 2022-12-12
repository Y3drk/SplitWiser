package com.splitwiser.splitwiserclient.controllers;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CreatePaymentController {

    private Stage dialogStage;

    private Payment payment;

    private boolean isApproved;

    @FXML
    private void initialize(){
        this.isApproved = false;
    }

    @FXML
    private void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPayment(Payment payment){
        this.payment = payment;
//        this.updateControls();
    }

    public boolean isApproved() {
        return isApproved;
    }

}
