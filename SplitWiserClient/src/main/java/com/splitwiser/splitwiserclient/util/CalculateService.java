package com.splitwiser.splitwiserclient.util;

import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private static String createNewSummaryLine(HashMap<User, BigDecimal> relations, User member){
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
        return newBalance;
    }

    private static void processMemberAsPayerPayments(HashMap<User, BigDecimal> relations, User member, Payment payment, ObservableList<User> receivers){
        //if they pay for the whole group
        int amountOfReceivers = receivers.size();
        if (amountOfReceivers > 1) {
            BigDecimal newValue = payment.getAmount().divide(BigDecimal.valueOf(amountOfReceivers), RoundingMode.HALF_DOWN);
            List<User> otherReceivers = receivers.filtered((elem) -> elem != member);
            for (User groupReceiver : otherReceivers
            ) {
                updateRelations(groupReceiver, relations, newValue.negate());
            }

            //if they pay for a single person
        } else {
            updateRelations(receivers.get(0), relations, payment.getAmount().negate());
        }
    }

    private static void processMemberAsReceiverPayments(HashMap<User, BigDecimal> relations, User member, Payment payment, User payer, ObservableList<User> receivers){
        int amountOfReceivers = receivers.size();
        //if it's a group payment
        if (amountOfReceivers > 1 && receivers.contains(member)) {
            BigDecimal newValue = payment.getAmount().divide(BigDecimal.valueOf(amountOfReceivers), RoundingMode.HALF_DOWN);
            updateRelations(payer, relations, newValue);
            //if it's a single person payment
        } else if (receivers.get(0).equals(member)) {
            updateRelations(payer, relations, payment.getAmount());
        }
    }

    private static void searchPaymentsListForGivenMember(User member, List<Payment> allPayments, HashMap<User, BigDecimal> relations){
        for (Payment payment : allPayments
        ) {
            User payer = payment.getPayer();
            ObservableList<User> receivers = payment.getReceivers();
            //if the member is a receiver
            if (!member.equals(payer)) {
                processMemberAsReceiverPayments(relations, member, payment, payer, receivers);
            }
            // if the member is a payer
            else {
                processMemberAsPayerPayments(relations, member, payment, receivers);
            }
        }
    }

    public static ObservableList<String> calculateBalanceBetweenAll(User user, ObservableList<Payment> allPayments) {
        List<User> groupMembers = user.getGroup().getMembers();
        ObservableList<String> balances = FXCollections.observableArrayList();

        for (User member : groupMembers
        ) {
            HashMap<User, BigDecimal> relations = new HashMap<>();
            searchPaymentsListForGivenMember(member, allPayments, relations);
            if (!relations.isEmpty()) {
                String newBalance = createNewSummaryLine(relations, member);
                if (!newBalance.equals("")) balances.add(newBalance);
            }
        }

        return balances;
    }

    public static List<Payment> calculateAggregatedPayments(User user, ObservableList<Payment> allPayments) {
        List<User> groupMembers = user.getGroup().getMembers();
        List<Payment> aggregatedPayments = new ArrayList<>();

        for (User member : groupMembers
        ) {
            HashMap<User, BigDecimal> relations = new HashMap<>();
            searchPaymentsListForGivenMember(member, allPayments, relations);
            if (!relations.isEmpty()) {
                //TODO: parse relations into aggregated payments
                List<User> payers = relations.keySet().stream().toList();
                for (User payer : payers
                ) {
                    if (relations.get(payer).compareTo(BigDecimal.valueOf(0)) > 0) {
                        aggregatedPayments.add(new Payment(payer.getGroup(), relations.get(payer).setScale(2, RoundingMode.HALF_DOWN), LocalDate.now(), "", payer, member));
                    }
                }
            }
        }

        return aggregatedPayments;
    }

    public static BigDecimal calculateUserBalance(User user, ObservableList<Payment> userInvolvedPayments) {
        BigDecimal currentBalance = BigDecimal.valueOf(0);
        for (Payment payment : userInvolvedPayments
        ) {
            ObservableList<User> receivers = payment.getReceivers();
            int amountOfReceivers = receivers.size();

            if (payment.getPayer().equals(user)) {
                //if the user pays for whole group
                if (amountOfReceivers > 1) {
                    BigDecimal toSubtract = payment.getAmount().divide(BigDecimal.valueOf(amountOfReceivers), RoundingMode.HALF_DOWN);
                    currentBalance = currentBalance.add(payment.getAmount()).subtract(toSubtract);
                }
                //if the user pays for some other user
                else {
                    currentBalance = currentBalance.add(payment.getAmount());
                    
                }
                //if the user is a group receiver
            } else if (amountOfReceivers > 1 && payment.getReceivers().contains(user)) {
                currentBalance = currentBalance.subtract(payment.getAmount().divide(BigDecimal.valueOf(amountOfReceivers), RoundingMode.HALF_DOWN));

            }
            // if the user is an individual receiver
            else if (receivers.get(0).equals(user)) {
                currentBalance = currentBalance.subtract(payment.getAmount());
            }
        }
        currentBalance = currentBalance.setScale(2, RoundingMode.HALF_DOWN);

        return currentBalance;
    }
}
