package com.splitwiser.splitwiserclient.data;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
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
    private Disposable refetchSub;


    public void refetchData() {
        if (this.refetchSub != null)
            this.refetchSub.dispose();
        this.payments.clear();
        this.users.clear();
        this.groups.clear();
        this.dataService.getGroupsInfo().blockingSubscribe(group -> {
            this.groups.add(group);
            for (User user : group.getMembers()) {
                user.setGroup(group);
                this.users.add(user);
            }
            for (Payment payment : group.getPayments()) {
                payment.setGroup(group);
                this.payments.add(payment);
            }
        }, throwable -> System.out.println("refetch error -> " + throwable.getMessage()));
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

    public Group getGroupData(Group group) {
        for (Group groupVar : this.groups) {
            if (groupVar.equals(group))
                return groupVar;
        }
        return null;
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

    public Observable<User> addUser(User user) {
        return this.dataService.createUser(user);
    }

    public Observable<Group> addGroup(Group group) {
        return this.dataService.createGroup(group);
    }

    public Observable<Payment> addPayment(Payment payment, int group_id) {
        return this.dataService.createPayment(payment, group_id);
    }
}
