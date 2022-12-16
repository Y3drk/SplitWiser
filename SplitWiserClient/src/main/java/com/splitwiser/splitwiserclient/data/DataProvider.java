package com.splitwiser.splitwiserclient.data;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import com.splitwiser.splitwiserclient.util.AlertGenerator;
import io.reactivex.rxjava3.core.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataProvider {

    private static DataProvider instance = null;

    public static DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    private final ObservableList<User> users = FXCollections.observableArrayList();

    private final ObservableList<Group> groups = FXCollections.observableArrayList();
    private final ObservableList<Payment> payments = FXCollections.observableArrayList();


    private final DataService dataService = new DataService();


    public synchronized void refetchData() {
        ObservableList<User> freshUsers = FXCollections.observableArrayList();
        ObservableList<Group> freshGroups = FXCollections.observableArrayList();
        ObservableList<Payment> freshPayments = FXCollections.observableArrayList();

        this.dataService.getGroupsInfo().blockingSubscribe(group -> {
            freshGroups.add(group);
            for (User user : group.getMembers()) {
                user.setGroup(group);
                freshUsers.add(user);
            }
            for (Payment payment : group.getPayments()) {
                payment.setGroup(group);
                freshPayments.add(payment);
            }
        }, throwable -> AlertGenerator.showErrorAlert("refetch error -> " + throwable.getMessage()));

        this.payments.setAll(freshPayments);
        this.groups.setAll(freshGroups);
        this.users.setAll(freshUsers);
    }

    public synchronized ObservableList<User> getUsersData() {
        return users;
    }

    public synchronized ObservableList<Group> getGroupsData() {
        return groups;
    }

    public synchronized ObservableList<Payment> getPaymentsData() {
        return payments;
    }

    public synchronized Group getGroupData(Group group) {
        for (Group groupVar : this.groups) {
            if (groupVar.equals(group))
                return groupVar;
        }
        return null;
    }

    public synchronized ObservableList<Payment> getAllUserInvolvedPayments(User user, ObservableList<Payment> allPayments) {
        ObservableList<Payment> userInvolvedPayments = FXCollections.observableArrayList();

        for (Payment payment : allPayments
        ) {
            if (payment.getPayer().equals(user)) {
                userInvolvedPayments.add(payment);
            } else if (payment.getReceivers().contains(user)) {
                userInvolvedPayments.add(payment);
            }
        }

        return userInvolvedPayments;
    }

    public synchronized Observable<User> addUser(User user) {
        return this.dataService.createUser(user);
    }

    public synchronized Observable<Group> addGroup(Group group) {
        return this.dataService.createGroup(group);
    }

    public synchronized Observable<Payment> addPayment(Payment payment, int group_id) {
        return this.dataService.createPayment(payment, group_id);
    }
}
