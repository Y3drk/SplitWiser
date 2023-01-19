package com.splitwiser.splitwiserclient.auxiliary.transitivity;

import com.splitwiser.splitwiserclient.model.category.Category;
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

public class GraphMatrixTransformer {

    private final ObservableList<User> members;

    private final HashMap<User, Integer> membersAsVertices;

    private final ObservableList<Payment> inputPayments;

    private BigDecimal[][] matrixInitialGraphRepresentation;

    private BigDecimal[][] matrixFinalGraphRepresentation;

    private ObservableList<Payment> outputPayments;


    public GraphMatrixTransformer(ObservableList<User> members, ObservableList<Payment> inputPayments){
        this.members = members;
        this.membersAsVertices = new HashMap<>();
        for (User member: members
             ) {
            this.membersAsVertices.put(member, members.indexOf(member));
        }

        this.inputPayments = inputPayments;
    }

    public ObservableList<Payment> getOutputPayments() {
        return outputPayments;
    }

    public void setMatrixFinalGraphRepresentation(BigDecimal[][] matrixFinalGraphRepresentation) {
        this.matrixFinalGraphRepresentation = matrixFinalGraphRepresentation;
    }

    public BigDecimal[][] getMatrixInitialGraphRepresentation() {
        return matrixInitialGraphRepresentation;
    }

    public void transformPaymentsToGraph(){
        this.matrixInitialGraphRepresentation = new BigDecimal[this.membersAsVertices.size()][this.membersAsVertices.size()];

        //initializing the matrix with 0s
        for(int row = 0; row < this.membersAsVertices.size(); row++){
            for(int col = 0; col < this.membersAsVertices.size(); col++) {
                this.matrixInitialGraphRepresentation[row][col] = BigDecimal.valueOf(0);
            }
        }

        //initializing edges based on list of aggregated payments
        for (Payment payment: this.inputPayments
             ) {
            Integer row = this.membersAsVertices.get(payment.getPayer());
            Integer column = this.membersAsVertices.get(payment.getReceivers().get(0)); //it's guaranteed that the aggregated payment only has one receiver

            this.matrixInitialGraphRepresentation[row][column] = payment.getAmount();
        }
    }


    public void transformGraphToPayments(){
        this.outputPayments = FXCollections.observableArrayList();
        for(int row = 0; row < this.membersAsVertices.size(); row++){
            for(int col = 0; col < this.membersAsVertices.size(); col++){
                BigDecimal cellValue = this.matrixFinalGraphRepresentation[row][col];
                if (!cellValue.equals(BigDecimal.valueOf(0))){
                    outputPayments.add(new Payment(this.members.get(row).getGroup(), cellValue, LocalDate.now(), "",this.members.get(row), this.members.get(col), Category.OTHER));
                }
            }
        }
    }
}
