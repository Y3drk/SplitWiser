package com.splitwiser.splitwiserclient.data;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
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

    private ObservableList<User> users = FXCollections.observableArrayList();

    private ObservableList<Group> groups = FXCollections.observableArrayList();
    private ObservableList<Payment> payments = FXCollections.observableArrayList();


    private final DataService dataService = new DataService();

    public void init() {
        refetchData();
    }

    public void refetchData(){
        this.payments.clear();
        this.users.clear();
        this.groups.clear();
        this.dataService.getGroupsInfo().subscribe(group -> {
            this.groups.add(group);
            for (User user : group.getMembers()) {
                user.setGroup(group);
                this.users.add(user);
            }
            for (Payment payment : group.getPayments()) {
                payment.setGroup(group);
                this.payments.add(payment);
            }
        });
    }

    public ObservableList<User> getUsersData() {
        return users;
    }

    public ObservableList<Group> getGroupsData() {
        return groups;
    }

    public ObservableList<Payment> getPaymentsData() {
        return payments;
    }

    public ObservableList<Payment> getAllUserInvolvedPayments(User user, ObservableList<Payment> allPayments) {
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

    public Observable<User> addUser(User user){
        return this.dataService.createUser(user);
    }
    public Observable<Group> addGroup(Group group){
        return this.dataService.createGroup(group);
    }
}
