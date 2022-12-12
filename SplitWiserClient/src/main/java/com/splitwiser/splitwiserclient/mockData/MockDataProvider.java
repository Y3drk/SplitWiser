package com.splitwiser.splitwiserclient.mockData;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class MockDataProvider {

    private static List<Group> groups = new ArrayList<>();

    private static List<User> users = new ArrayList<>();

    private static List<Payment> payments = new ArrayList<>();

    public static void init() {
        Group group1 = new Group("G1");
        Group group2 = new Group("G2");

        groups.add(group1);
        groups.add(group2);

        User user1 = new User("Jan", "Kowalski", group1);
        User user2 = new User("Adam", "Nowak", group1);
        User user3 = new User("Jane", "Doe", group1);
        User user4 = new User("John", "Doe", group1);
        group1.addUser(user1);
        group1.addUser(user2);
        group1.addUser(user3);
        group1.addUser(user4);

        User user5 = new User("Mark", "Smith", group2);
        group2.addUser(user5);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        //group payment for Jane Doe
        Payment payment1 = new Payment(group1, BigDecimal.valueOf(100.50), new Date(22, Calendar.NOVEMBER, 27), "Dinner together", user1);

        //group payment where Jane paid
        Payment payment2 = new Payment(group1, BigDecimal.valueOf(36.95), new Date(22, Calendar.DECEMBER, 12), "Bowling alley payment", user3);

        //single person payments for Jane Doe
        Payment payment3 = new Payment(group1, BigDecimal.valueOf(9.50), new Date(22, Calendar.OCTOBER, 19), "Butter", user4, user3);
        Payment payment4 = new Payment(group1, BigDecimal.valueOf(35.50), new Date(22, Calendar.AUGUST, 18), "", user1, user3);
        Payment payment5 = new Payment(group1, BigDecimal.valueOf(17.00), new Date(22, Calendar.OCTOBER, 23), "Uber", user2, user3);

        //single person payment where Jane paid
        Payment payment6 = new Payment(group1, BigDecimal.valueOf(21.37), new Date(22, Calendar.OCTOBER, 12), "Drinks at C&U's", user3, user1);


        //other single person payments for Jan, Adam and John
        Payment payment7 = new Payment(group1, BigDecimal.valueOf(10.50), new Date(22, Calendar.AUGUST, 27), "Beers", user1, user2);
        Payment payment8 = new Payment(group1, BigDecimal.valueOf(10.50), new Date(22, Calendar.NOVEMBER, 27), "", user2, user4);
        Payment payment9 = new Payment(group1, BigDecimal.valueOf(42.00), new Date(22, Calendar.DECEMBER, 13), "pizza", user1, user4);

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
        payments.add(payment4);
        payments.add(payment5);
        payments.add(payment6);
        payments.add(payment7);
        payments.add(payment8);
        payments.add(payment9);

        group1.addPayment(payment1);
        group1.addPayment(payment2);
        group1.addPayment(payment3);
        group1.addPayment(payment4);
        group1.addPayment(payment5);
        group1.addPayment(payment6);
        group1.addPayment(payment7);
        group1.addPayment(payment8);
        group1.addPayment(payment9);

    }

    public static ObservableList<User> getMockUsers() {

        ObservableList<User> passedUsers = FXCollections.observableArrayList();

        passedUsers.addAll(users);

        return passedUsers;
    }

    public static ObservableList<Payment> getMockPayments() {
        ObservableList<Payment> passedPayments = FXCollections.observableArrayList();

        passedPayments.addAll(payments);

        return passedPayments;
    }

    public static void addGroup(Group group) {
        groups.add(group);
    }

    public static void addUser(User user) {
        users.add(user);
        Group group = user.getGroup();
        group.addUser(user);
        groups.add(group);
    }

    public static void addPayment(Payment payment) {
        payments.add(payment);
        Group group = payment.getGroup();

        group.addPayment(payment);
        groups.add(group);
    }

    //some methods that simulate operations that would be done on the servers side
    public static ObservableList<Payment> getAllUserInvolvedPayments(User user, ObservableList<Payment> allPayments) {
        ObservableList<Payment> userInvolvedPayments = FXCollections.observableArrayList();

        for (Payment payment : allPayments
        ) {
            if (payment.getPayer() == user) {
                userInvolvedPayments.add(payment);
            } else if (payment.getReceiver() == null || payment.getReceiver() == user) {
                userInvolvedPayments.add(payment);
            }
        }

        return userInvolvedPayments;
    }

    //TODO: implement this as simple as possible -> done
    // and implement total summary between people
    public static BigDecimal getUsersBalance(User user, ObservableList<Payment> userInvolvedPayments) {
        BigDecimal currentBalance = BigDecimal.valueOf(0);
        for (Payment payment : userInvolvedPayments
        ) {
            User receiver = payment.getReceiver();
            if (payment.getPayer() == user) {
                //if the user pays for whole group
                if (receiver == null){
                    int amountOfMembers = user.getGroup().getAmountOfMembers();
                    double multiplyBy = (amountOfMembers - 1) / (double) amountOfMembers;
                    currentBalance = currentBalance.add(payment.getValue().multiply(BigDecimal.valueOf(multiplyBy)));
                }
                //if the user pays for some other user
                else {
                    currentBalance = currentBalance.add(payment.getValue());
                }
                //if the user is a group receiver
            } else if (receiver == null) {
                int divideBy = user.getGroup().getAmountOfMembers();
                currentBalance = currentBalance.subtract(payment.getValue().divide(BigDecimal.valueOf(divideBy)));
            }
            // if the user is an individual receiver
            else {
                currentBalance = currentBalance.subtract(payment.getValue());
            }
        }
        currentBalance = currentBalance.setScale(2, RoundingMode.HALF_DOWN);

        return currentBalance;
    }

    public static ObservableList<String> calculateBalanceBetweenAll(User user, ObservableList<Payment> allPayments) {
        List<User> groupMembers = user.getGroup().getMembers();
        ObservableList<String> balances = FXCollections.observableArrayList();

        for (User member : groupMembers
        ) {
            HashMap<User, BigDecimal> relations = new HashMap<>();
            for (Payment payment : allPayments
            ) {
                User payer = payment.getPayer();
                if (member != payer) {
                    User receiver = payment.getReceiver();
                    if (receiver == null) {
                        int divideBy = user.getGroup().getAmountOfMembers();
                        BigDecimal newValue = payment.getValue().divide(BigDecimal.valueOf(divideBy));
                        updateRelations(payer, relations, newValue);

                    } else if (receiver == user) {
                        updateRelations(payer, relations, payment.getValue());
                    }
                }
                else {
                    User receiver = payment.getReceiver();
                    if (receiver == null) {
                        int divideBy = user.getGroup().getAmountOfMembers();
                        BigDecimal newValue = payment.getValue().divide(BigDecimal.valueOf(divideBy)).negate();
                        List<User> receivers = user.getGroup().getMembers().filtered((elem) -> elem != member);
                        for (User groupReceiver : receivers
                        ) {
                            updateRelations(groupReceiver, relations, newValue);
                        }


                    } else {
                        updateRelations(receiver, relations, payment.getValue().negate());
                    }
                }
            }
            if (!relations.isEmpty()) {
                String newBalance = "";
                List<User> payers = relations.keySet().stream().toList();
                for (User payer : payers
                ) {
                    if (relations.get(payer).compareTo(BigDecimal.valueOf(0)) > 0) {
                        if (newBalance.equals("")){
                            newBalance = member.getFirstName() + " " + member.getLastName() + " owns: ";
                        }
                        newBalance = newBalance.concat(payer.getFirstName() + " " + payer.getLastName() + " -> " + relations.get(payer).setScale(2, RoundingMode.HALF_DOWN).toString() + "$; ");
                    }
                }
                if (!newBalance.equals("")) balances.add(newBalance);
            }
        }

        return balances;
}

    private static void updateRelations(User payer, HashMap<User, BigDecimal> relations, BigDecimal newValue) {
        if (relations.containsKey(payer)) {
            BigDecimal oldValue = relations.get(payer);
            relations.put(payer, oldValue.add(newValue));
        } else {
            relations.put(payer, newValue);
        }
    }
}
