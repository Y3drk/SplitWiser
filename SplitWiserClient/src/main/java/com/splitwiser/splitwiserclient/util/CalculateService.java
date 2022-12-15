package com.splitwiser.splitwiserclient.util;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class CalculateService {

    private static void updateRelations(User payer, HashMap<User, BigDecimal> relations, BigDecimal newValue) {
        if (relations.containsKey(payer)) {
            BigDecimal oldValue = relations.get(payer);
            relations.put(payer, oldValue.add(newValue));
        } else {
            relations.put(payer, newValue);
        }
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
                ObservableList<User> receivers = payment.getReceivers();
                int amountOfReceivers = receivers.size();
                //if the member is a receiver
                if (member.getId() != payer.getId()) {
                    //if it's a group payment
                    if (amountOfReceivers > 1 && receivers.contains(member)) {
                        BigDecimal newValue = payment.getValue().divide(BigDecimal.valueOf(amountOfReceivers));
                        updateRelations(payer, relations, newValue);
                        //if it's a single person payment
                    } else if (receivers.get(0) == member) {
                        updateRelations(payer, relations, payment.getValue());
                    }
                }
//                if the member is a payer
                else {
                    //if they pay for the whole group
                    if (amountOfReceivers > 1) {
                        BigDecimal newValue = payment.getValue().divide(BigDecimal.valueOf(amountOfReceivers));
                        List<User> otherReceivers = receivers.filtered((elem) -> elem != member);
                        for (User groupReceiver : otherReceivers
                        ) {
                            updateRelations(groupReceiver, relations, newValue.negate());
                        }

                        //if they pay for a single person
                    } else {
                        updateRelations(receivers.get(0), relations, payment.getValue().negate());
                    }
                }
            }
            if (!relations.isEmpty()) {
                String newBalance = "";
                List<User> payers = relations.keySet().stream().toList();
                for (User payer : payers
                ) {
                    if (relations.get(payer).compareTo(BigDecimal.valueOf(0)) > 0) {
                        if (newBalance.equals("")) {
                            newBalance = member.getFirstName() + " " + member.getLastName() + " owns: ";
                        }
                        newBalance = newBalance.concat(payer.getFirstName() + " " + payer.getLastName() + " -> " + relations.get(payer).setScale(2, RoundingMode.HALF_DOWN) + "$; ");
                    }
                }
                if (!newBalance.equals("")) balances.add(newBalance);
            }
        }

        return balances;
    }

    public static BigDecimal calculateUserBalance(User user, ObservableList<Payment> userInvolvedPayments) {
        BigDecimal currentBalance = BigDecimal.valueOf(0);
        for (Payment payment : userInvolvedPayments
        ) {
            int amountOfReceivers = payment.getReceivers().size();
            if (payment.getPayer() == user) {
                //if the user pays for whole group
                if (amountOfReceivers > 1) {
                    double multiplyBy = (amountOfReceivers - 1) / (double) amountOfReceivers;
                    currentBalance = currentBalance.add(payment.getValue().multiply(BigDecimal.valueOf(multiplyBy)));
                }
                //if the user pays for some other user
                else {
                    currentBalance = currentBalance.add(payment.getValue());
                }
                //if the user is a group receiver
            } else if (amountOfReceivers > 1) {
                currentBalance = currentBalance.subtract(payment.getValue().divide(BigDecimal.valueOf(amountOfReceivers)));
            }
            // if the user is an individual receiver
            else {
                currentBalance = currentBalance.subtract(payment.getValue());
            }
        }
        currentBalance = currentBalance.setScale(2, RoundingMode.HALF_DOWN);

        return currentBalance;
    }
}
