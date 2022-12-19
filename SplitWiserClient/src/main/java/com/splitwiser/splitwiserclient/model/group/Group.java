package com.splitwiser.splitwiserclient.model.group;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {

    private int id;

    private StringProperty name;

    private ObservableList<User> members;

    private ObservableList<Payment> payments;

    // for Jackson
    public Group() {
        this("newGroup", new ArrayList<>());
    }

    public Group(String name) {
        this(name, new ArrayList<>());
    }


    public Group(String name, List<User> members) {
        this.name = new SimpleStringProperty(name);
        this.members = FXCollections.observableArrayList(members);
        this.payments = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setMembers(List<User> members) {
        this.members = FXCollections.observableArrayList(members);
    }
    public ObservableList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = FXCollections.observableArrayList(payments);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void addUser(User user) {
        this.members.add(user);
    }

    public void setName(String newName) {
        this.name = new SimpleStringProperty(newName);
    }

    public int getAmountOfMembers() {
        return this.members.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
