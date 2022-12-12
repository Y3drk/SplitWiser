package com.splitwiser.splitwiserclient.auxiliary;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PaymentCellFactory implements Callback<ListView<Payment>, ListCell<Payment>> {
    @Override
    public ListCell<Payment> call(ListView<Payment> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(Payment payment, boolean empty) {
                super.updateItem(payment, empty);
                if (empty || payment == null) {
                    setText(null);
                } else {
                    String receiver = "All";
                    User receiverUser = payment.getReceiver();
                    if (receiverUser != null){
                        receiver = receiverUser.getFirstName() + " " + receiverUser.getLastName();
                    }
                    setText(payment.getPayer().getFirstName() + " "+ payment.getPayer().getLastName() + ", " + payment.getDate() + ", " + payment.getValue().toString() + ", " + receiver);
                }
            }
        };
    }
} 

