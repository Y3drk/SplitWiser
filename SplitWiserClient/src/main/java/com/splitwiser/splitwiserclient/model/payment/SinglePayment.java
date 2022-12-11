package com.splitwiser.splitwiserclient.model.payment;

import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.ObjectProperty;

public class SinglePayment extends Payment {

    private ObjectProperty<User> receiver;

    public User getReceiver() {
        return receiver.get();
    }

    public ObjectProperty<User> receiverProperty() {
        return receiver;
    }
}
