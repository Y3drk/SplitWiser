package com.splitwiser.splitwiserclient.model.group;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
    private StringProperty name;

    private ObservableList<User> members;

    private ObservableList<Payment> payments;

    public Group(String name){
        this.name = new SimpleStringProperty(name);
        this.members = FXCollections.observableArrayList();
        this.payments = FXCollections.observableArrayList();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<User> getMembers() {
        return members;
    }

    public ObservableList<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

    public void addUser(User user){
        this.members.add(user);
    }
}
