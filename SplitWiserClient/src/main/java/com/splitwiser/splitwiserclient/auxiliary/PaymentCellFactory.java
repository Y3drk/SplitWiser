package com.splitwiser.splitwiserclient.auxiliary;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.ObservableList;
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
                    ObservableList<User> receivers = payment.getReceivers();
                    if (receivers.size() == 1){
                        receiver = receivers.get(0).getFirstName() + " " + receivers.get(0).getLastName();
                    }
                    setText(payment.getPayer().getFirstName() + " "+ payment.getPayer().getLastName() + ", " + payment.getDate() + ", " + payment.getAmount().toString() + ", " + receiver + ", "+ payment.getDescription());
                    setMouseTransparent(true);
                    setFocusTraversable(false);
                }
            }
        };
    }
} 

